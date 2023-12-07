const comentarios = document.getElementById('comentarios');
const enviar = document.getElementById('enviar');
const form = document.getElementById('form');
const urlParams = new URLSearchParams(window.location.search);
getComentarios()
function getComentarios() {
    fetch("/get-comentarios-artesano?id=" + urlParams.get('id'))
        .then(response => response.json())
        .then(data => {
            comentarios.innerHTML = "";
            for (let elem in data) {
                console.log(data[elem]);
                comentarios.innerHTML += "<div class='comentario'>" +
                    "<h3>"+ data[elem].nombre +":</h3><p>" + data[elem].comentario + "</p>" +
                    "</div>";
            }
        });
}
enviar.addEventListener('click', (e) => {
    e.preventDefault();
    let url = '/post-comentario-artesano';
    let data = new FormData(form);

    data.append('id', urlParams.get('id'));
    console.log(data.get('id'));
    let xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = () => {
        if (xhr.status == 200) {
            console.log(xhr.response);
            getComentarios();
            form.reset();
        }
        else {
            console.log(xhr.status);
            console.log(xhr.response);
        }
    }
    xhr.send(data);
});

