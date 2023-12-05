/*
* Grafica los datos de los artesanos
 */
const HTTP = new XMLHttpRequest()
HTTP.open("GET", 'artesano-data')
HTTP.onload = function() {
    if (HTTP.status === 200) {
        let data = JSON.parse(HTTP.responseText)
        console.log(data)
        graficar(data)
    } else {
        console.error('Error:', HTTP.statusText)
    }
}
HTTP.onerror = function() {
    console.error('Error de red')
}
HTTP.send()

function graficar(data) {
    let  tipos = [], cant = []
    for (let key in data){
        if (data[key] != 0){
            tipos.push(key)
            cant.push(data[key])
        }
    }
    const CHART = Highcharts.chart('artesanos', {
        chart: {
            type: 'bar'
        },
        title: {
            text: 'Número de Artesanos según el tipo de artesania'
        },
        xAxis: {
            categories: tipos
        },
        yAxis: {
            title: {
                text: 'Cantidad de Artesanos'
            }
        },
        series: [{
            name: '',
            data: cant
        }]
    });
}
/*
* Grafica los datos de los hinchas
 */
const HTTPHincha = new XMLHttpRequest()
HTTPHincha.open("GET", 'hincha-data')
HTTPHincha.onload = function() {
    if (HTTPHincha.status === 200) {
        let data = JSON.parse(HTTPHincha.responseText)
        graficarHincha(data)
    } else {
        console.error('Error:', HTTPHincha.statusText)
    }
}
HTTPHincha.onerror = function() {
    console.error('Error de red')
}
HTTPHincha.send()

function graficarHincha(data) {
    let  tipos = [], cant = []
    for (let key in data){
        if (data[key] != 0){
            tipos.push(key)
            cant.push(data[key])
        }
    }
    const CHART = Highcharts.chart('hinchas', {
        chart: {
            type: 'bar'
        },
        title: {
            text: 'Número de Hinchas por deporte'
        },
        xAxis: {
            categories: tipos
        },
        yAxis: {
            title: {
                text: 'Cantidad de Hinchas'
            }
        },
        series: [{
            name: '',
            data: cant
        }]
    });
}

