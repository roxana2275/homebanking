Vue.createApp({
    data() {
        return {
            clientAccounts: [],
            clientAccountsTo: [],
            debitCards: [],
            errorToats: null,
            errorMsg: null,
            accountFromNumber: "VIN",
            accountToNumber: "VIN",
            trasnferType: "own",
            amount: 0,
            description: ""
        }
    },
    methods: {
        getData: function () {
            axios.get("/api/clients/current/accounts")
                .then((response) => {
                    //get client ifo
                    this.clientAccounts = response.data;
                })
                .catch((error) => {
                    console.log(error);
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        checkTransfer: function () {
          if (this.accountFromNumber === "VIN") {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'You must select an origin account',
            });
          } else if (this.accountToNumber === "VIN") {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'You must select a destination account',
            });
          } else if (this.amount === 0) {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'You must indicate an amount',
            });
          } else if (this.description.length <= 0) {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'You must indicate a description',
            });
          } else {
            // Mostrar una alerta de confirmación con SweetAlert
            Swal.fire({
              title: 'Confirm Funds Transfer',
              text: 'Transfer cannot be undone, do you want to continue?',
              icon: 'question',
              showCancelButton: true,
              confirmButtonColor: '#3085d6',
              cancelButtonColor: '#d33',
              confirmButtonText: 'Yes, transfer the funds',
              cancelButtonText: 'Cancel',
            }).then((result) => {
              if (result.isConfirmed) {
                // Realizar la transferencia
                let config = {
                  headers: {
                    'content-type': 'application/x-www-form-urlencoded',
                  },
                };

                axios
                  .post(
                    `/api/transactions?fromAccountNumber=${this.accountFromNumber}&toAccountNumber=${this.accountToNumber}&amount=${this.amount}&description=${this.description}`,
                    config
                  )
                  .then((response) => {
                    this.modal.hide();
                    Swal.fire({
                      icon: 'success',
                      title: 'Transaction complete!',
                      text: 'The funds have been transferred!',
                    }).then(() => {
                      this.okmodal.show();
                    });
                  })
                  .catch((error) => {
                    console.log(error);
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                  });
              }
            });
          }
        },
    
        changedType: function () {
            this.accountFromNumber = "VIN";
            this.accountToNumber = "VIN";
        },
        changedFrom: function () {
            if (this.trasnferType == "own") {
                this.clientAccountsTo = this.clientAccounts.filter(account => account.number != this.accountFromNumber);
                this.accountToNumber = "VIN";
            }
        },
        finish: function () {
            window.location.reload();
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {

                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.modal = new bootstrap.Modal(document.getElementById('confirModal'));
        this.okmodal = new bootstrap.Modal(document.getElementById('okModal'));
        this.getData();
    }
}).mount("#app");