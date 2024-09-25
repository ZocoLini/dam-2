import math

barras_viejas = int(input("Cantidad de barras que no son del dia vendidas: "))

print("El precio habitual de la barra es: 3.49€")
print("Por n ser barras frescas se hará un descuento del 60%")
total = barras_viejas * 3.49 * 0.6
print("El coste final es de: " + str(total.__round__(2)))