"""Imprime datos de pareto."""
import plotly
from plotly.graph_objs import Scatter, Layout
print plotly.__version__  # version >1.9.4 required

archivillo = open('resultados.txt')
x = []
y = []
indice = 0
for linea in archivillo:
    numeros = linea.split(' ', 2)
    x.append(numeros[0])
    y.append(numeros[1])
    # 0 azul
    # 1 naranja
    # 4 verde
plotly.offline.plot({
    "data": [
        Scatter(x=x, y=y, mode="markers")
        ],
    "layout": Layout(
        title="Prueba"
        )
})
