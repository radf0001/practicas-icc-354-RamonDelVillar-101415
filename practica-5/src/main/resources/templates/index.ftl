<!DOCTYPE html>
<html
        class="h-full bg-slate-100"
        lang="en">
<head>
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script>
        let cliente1 = [];
        let cliente2= [];
        let cliente1Humedad = [];
        let cliente2Humedad = [];
        let cliente1Temperatura = [];
        let cliente2Temperatura = [];
        let chartCliente1Humedad;
        let chartCliente1Temperatura;
        let chartCliente2Humedad;
        let chartCliente2Temperatura;
        var stompClient = null;
        window.onload = function () {
            cliente1 = [<#list cliente1 as c1>${c1?c},</#list>];
            cliente2 = [<#list cliente2 as c1>${c1?c},</#list>];
            let json;
            cliente1.forEach(function (arrayItem) {
                json = JSON.parse(arrayItem);
                cliente1Humedad.push({label: json.fechaGeneracion, y: json.humedad});
                cliente1Temperatura.push({label: json.fechaGeneracion, y: json.temperatura});
            });
            cliente2.forEach(function (arrayItem) {
                json = JSON.parse(arrayItem);
                cliente2Humedad.push({label: json.fechaGeneracion, y: json.humedad});
                cliente2Temperatura.push({label: json.fechaGeneracion, y: json.temperatura});
            });
            chartCliente1Humedad = new CanvasJS.Chart("chartContainer", {
                animationEnabled: true,
                theme: "light2",
                title:{
                    text: "Cliente 1 Humedad"
                },
                data: [{
                    type: "line",
                    indexLabelFontSize: 16,
                    dataPoints: cliente1Humedad
                }]
            });
            chartCliente1Humedad.render();

            chartCliente1Temperatura= new CanvasJS.Chart("chartContainer1", {
                animationEnabled: true,
                theme: "light2",
                title:{
                    text: "Cliente 1 Temperatura"
                },
                data: [{
                    type: "line",
                    indexLabelFontSize: 16,
                    dataPoints: cliente1Temperatura
                }]
            });
            chartCliente1Temperatura.render();

            chartCliente2Humedad = new CanvasJS.Chart("chartContainer2", {
                animationEnabled: true,
                theme: "light2",
                title:{
                    text: "Cliente 2 Humedad"
                },
                data: [{
                    type: "line",
                    indexLabelFontSize: 16,
                    dataPoints: cliente2Humedad
                }]
            });
            chartCliente2Humedad.render();

            chartCliente2Temperatura = new CanvasJS.Chart("chartContainer3", {
                animationEnabled: true,
                theme: "light2",
                title:{
                    text: "Cliente 2 Temperatura"
                },
                data: [{
                    type: "line",
                    indexLabelFontSize: 16,
                    dataPoints: cliente2Temperatura
                }]
            });
            chartCliente2Temperatura.render();

        }

        function connect() {
            var socket = new SockJS('/stomp-endpoint');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/notificacion_sensores', function (greeting) {
                    showGreeting(JSON.parse(greeting.body));
                });
            });
        }

        function showGreeting(message) {
            if (message.idDispositivo === 1){
                chartCliente1Humedad.options.data[0].dataPoints.push({label:message.fechaGeneracion,y: message.humedad});
                chartCliente1Humedad.render();
                chartCliente1Temperatura.options.data[0].dataPoints.push({label:message.fechaGeneracion,y: message.temperatura});
                chartCliente1Temperatura.render();
            }else if (message.idDispositivo === 2){
                chartCliente2Humedad.options.data[0].dataPoints.push({label:message.fechaGeneracion,y: message.humedad});
                chartCliente2Humedad.render();
                chartCliente2Temperatura.options.data[0].dataPoints.push({label:message.fechaGeneracion,y: message.temperatura});
                chartCliente2Temperatura.render();
            }
        }

        function sendName(idDispositivo) {
            stompClient.send("/app/hello", {}, idDispositivo);
        }

        $(function () {
            $("#send1").click(function() { sendName(1); });
            $("#send2").click(function() { sendName(2); });
        });

        connect();
    </script>
    <meta charset="UTF-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.tailwindcss.com"></script>

    <link
            rel="preconnect"
            href="https://fonts.googleapis.com" />
    <link
            rel="preconnect"
            href="https://fonts.gstatic.com"
            crossorigin />
    <link
            href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap"
            rel="stylesheet" />

    <style>
        * {
            font-family: 'Inter', system-ui, -apple-system, BlinkMacSystemFont,
            'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans',
            'Helvetica Neue', sans-serif;
        }
    </style>

    <title>App</title>
</head>
<body class="flex flex-col items-baseline justify-center min-h-screen">
<div
        class="flex flex-col gap-2 mb-8 lg:mb-16 md:flex-row md:justify-center">
    <a
            id="send1"
            href="#"
            type="button"
            class="text-white bg-sky-700 hover:bg-sky-800 focus:ring-4 focus:ring-sky-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2">
        Generar Cliente 1
    </a>

    <a
            id="send2"
            href="#"
            type="button"
            class="text-white bg-teal-700 hover:bg-teal-800 focus:ring-4 focus:ring-teal-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2">
        Generar Cliente 2
    </a>
</div>
<div id="chartContainer" class="m-auto mb-8" style="height: 300px; width: 50%;"></div>
<div id="chartContainer1" class="m-auto mb-8" style="height: 300px; width: 50%;"></div>
<div id="chartContainer2" class="m-auto mb-8" style="height: 300px; width: 50%;"></div>
<div id="chartContainer3" class="m-auto mb-8" style="height: 300px; width: 50%;"></div>
<script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
</body>
</html>
