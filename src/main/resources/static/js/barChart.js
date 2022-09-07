 var real_data = /*[[${chartData}]]*/'noValue';
      $(window).bind("load", function () {
            google.charts.load('current', {
                packages : [ 'corechart', 'bar' ]
            });
            google.charts.setOnLoadCallback(drawColumnChart);
        });
        function drawColumnChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Year');
            data.addColumn('number', 'Views');
            Object.keys(real_data).forEach(function(key) {
                data.addRow([ key, real_data[key] ]);
            });
            var options = {
                title : 'Blog stats',
                hAxis : {
                    title : 'Years',
                },
                vAxis : {
                    title : 'View Count'
                }
            };
            var chart = new google.visualization.ColumnChart(document
                    .getElementById('chart_div'));
            chart.draw(data, options);
        }