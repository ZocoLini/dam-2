import math

dinero = int(input("Cantidad a invertir: "))
interes = 1 + int(input("Interés anual: "))
tiempo = int(input("Años: "))

print(dinero * math.pow(interes, tiempo))