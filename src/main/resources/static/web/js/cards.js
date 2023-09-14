Vue.createApp({
  data() {
    return {
      clientInfo: {},
      creditCards: [],
      debitCards: [],
      selectedCardToDelete: '',
      errorToats: null,
      errorMsg: null,
      infoBoxVisible: false,
    };
  },
  methods: {
    getData: function () {
     axios
        .get("/api/clients/current")
        .then((response) => {
          this.clientInfo = response.data;
          this.creditCards = this.clientInfo.cards.filter(
            (card) => card.type === "CREDIT"&&card.active==true
          );
          this.debitCards = this.clientInfo.cards.filter(
            (card) => card.type === "DEBIT"&&card.active==true
          );
          console.log('Credit Cards:', this.debitCards);
        })
        .catch((error) => {
          this.errorMsg = "Error al obtener los datos";
          this.errorToats.show();
        });
    },
    formatDate: function (date) {
      return new Date(date).toLocaleDateString("en-gb");
    },
confirmDelete(cardNumber) {
  Swal.fire({
    title: 'Do you want to delete the card?',
    showDenyButton: true,
    showCancelButton: true,
    confirmButtonText: 'Delete',
    denyButtonText: `Go back`,
    }).then((result) => {
       if (result.isConfirmed) {
          this.selectedCardToDelete = cardNumber
          axios.patch(`/api/clients/current/cards?cardNumber=${this.selectedCardToDelete}`)
          .then(res => {
             if (res.status === 200) {
                   Swal.fire({
                           position: 'center',
                           icon: 'success',
                           title: 'Card deleted',
                           showConfirmButton: false,
                           timer: 1500
                         });
                         setTimeout(() => {
                           window.location.reload();
                         }, 1800);
          } else {
                // La solicitud tuvo éxito pero la respuesta no es 200 (puedes manejar otros códigos de estado aquí)
                Swal.fire({
                  position: 'center',
                  title: 'Card deletion failed',
                  showConfirmButton: false,
                  timer: 1500
                });
              }
            })
    .catch(err => {
      console.log(err)
      Swal.fire({
          position: 'center',
          title: 'Card cant be deleted, try again!',
          showConfirmButton: false,
          timer: 1500
      })
    })
     } else if (result.isDenied) {
      Swal.fire('Card not deleted', '', 'info')
      }
    })
  },
    signOut: function () {
      axios
        .post("/api/logout")
        .then((response) => (window.location.href = "/web/index.html"))
        .catch(() => {
          this.errorMsg = "Fallo al cerrar sesión";
          this.errorToats.show();
        });
    },
  },
  mounted: function () {
    this.errorToats = new bootstrap.Toast(document.getElementById("danger-toast"));
    this.getData();
  },
}).mount("#app");

