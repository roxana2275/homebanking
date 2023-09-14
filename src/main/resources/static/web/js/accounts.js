Vue.createApp({
    data() {
        return {
            clientInfo: {},
            accounts: [],
            errorToats: null,
            errorMsg: null,
            selectedAccountToDelete: '',
        }
    },
     created() {
                this.getActiveAccounts()
            },
    methods: {
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    //get client ifo
                    this.clientInfo = response.data;
                    console.log(this.clientInfo.accounts)
                })
                .catch((error) => {
                    // handle error
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
         getActiveAccounts() {
                        axios.get(`/api/clients/current/accounts`)
                            .then(res => {
                                this.accounts = res.data.sort((a, b) => b.id - a.id)
                            }).catch(err => console.log(err))
                    },
        deleteAccount(accountNumber) {
                        Swal.fire({
                            title: 'Do you want to delete the card?',
                            showDenyButton: true,
                            showCancelButton: true,
                            confirmButtonText: 'Delete Account',
                            denyButtonText: `Go back`,
                        }).then((res) => {
                            if (res.isConfirmed) {
                                this.selectedAccountToDelete = accountNumber
                                axios.patch(`/api/clients/current/accounts?number=${this.selectedAccountToDelete}`)
                                    .then(res => {
                                        Swal.fire({
                                            position: 'center',
                                            icon: 'success',
                                            title: 'Account Deleted',
                                            showConfirmButton: false,
                                            timer: 1500
                                        })
                                        setTimeout(() => {
                                            window.location.reload();
                                        }, 1800)
                                    }).catch(err => {
                                        this.errMsg2 = err.response.data
                                        Swal.fire({
                                            position: 'center',
                                            icon: 'error',
                                            title: `${this.errMsg2}`,
                                            showConfirmButton: false,
                                            timer: 2000
                                        })
                                    })
                            }
                        })
                    },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
        create: function () {
            axios.post('/api/clients/current/accounts')
                .then(response => window.location.reload())
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
        }
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
}).mount('#app')