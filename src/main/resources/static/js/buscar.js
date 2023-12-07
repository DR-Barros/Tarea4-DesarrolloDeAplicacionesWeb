
const buscador = document.getElementById("buscador");
const hincha = document.getElementById("hincha");
const artesano = document.getElementById("artesano");
const resultado = document.getElementById("resultado");
buscador.addEventListener("input", buscar);
buscar(null);
hincha.addEventListener("change", (e)=>{
    if (e.target.checked) {
        artesano.checked = false;
    } else {
        artesano.checked = true;
    }
    buscar(e);
});
artesano.addEventListener("change", (e)=> {
    if (e.target.checked) {
        hincha.checked = false;
    } else {
        hincha.checked = true;
    }
    buscar(e);
});

function buscar(e) {
    let valor = buscador.value;
    if (valor.length >= 3) {
        let url = "/buscador?busqueda=" + valor+"&hincha="+hincha.checked+"&artesano="+artesano.checked;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                if (data.artesanos != undefined){
                    resultado.innerHTML = "<h2>Artesanos</h2>";
                    for (let elem in data.artesanos) {
                        //console.log(data.artesanos[elem]);
                        resultado.innerHTML += "<div class='data artesano' id='"+data.artesanos[elem].a.id+"'>" +
                            "<h3>Nombre </h3><p>"+data.artesanos[elem].a.nombre + "</p>" +
                            "<h3>Mail: </h3><p>"+data.artesanos[elem].a.email + "</p>" +
                            "<h3>Comuna: </h3><p>"+data.artesanos[elem].a.comuna.nombre + "</p>" +
                            "<h3>Comentarios: </h3><p>"+data.artesanos[elem].b + "</p>" +
                            "</div>";
                    }
                    let artesano = document.getElementsByClassName("artesano")
                    artesano = Array.from(artesano)
                    artesano.forEach(element => {
                        element.addEventListener("click", e => {
                            window.location.href = "./comentarios-artesano?id="+ String(element.id);
                        })
                    });
                } else {
                    resultado.innerHTML = "<h2>Hinchas</h2>";
                    for (let elem in data.hinchas) {
                        //console.log(data.hinchas[elem]);
                        resultado.innerHTML += "<div class='data hincha' id='"+data.hinchas[elem].a.id+"'>" +
                            "<h3>Nombre: </h3><p>" + data.hinchas[elem].a.nombre + "</p>" +
                            "<h3>Mail: </h3><p>" + data.hinchas[elem].a.email + "</p>" +
                            "<h3>Comuna: </h3><p>" + data.hinchas[elem].a.comuna.nombre + "</p>" +
                            "<h3>Comentarios: </h3><p>" + data.hinchas[elem].b + "</p>" +
                            "</div>";
                    }
                    let hincha = document.getElementsByClassName("hincha")
                    hincha = Array.from(hincha)
                    hincha.forEach(element => {
                        element.addEventListener("click", e => {
                            window.location.href = "./comentarios-hincha?id="+ String(element.id);
                        })
                    });
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    } else {
        resultado.innerHTML = "<h2>Buscador</h2>";
    }
}