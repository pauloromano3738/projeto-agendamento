import { Modal } from 'bootstrap'

document.addEventListener('DOMContentLoaded', function () {
    var urlParams = new URLSearchParams(window.location.search);
    var idProfissional = urlParams.get('idProfissional');
    var idCliente = urlParams.get('idCliente');
    var idAgendamento = urlParams.get('idAgendamento');

    if ((idProfissional || idCliente || idAgendamento) && sessionStorage.getItem('openModal') === 'true') {
        var myModal = new Modal(document.getElementById('exampleModal'));
        myModal.show();
    }

    var modalLink = document.getElementById('openModalLink');
    if (modalLink) {
        modalLink.addEventListener('click', function () {
            sessionStorage.setItem('openModal', 'true');
        });
    }
});
