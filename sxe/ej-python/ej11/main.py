import math

dinero_inicial = int(input("Cantidad inicial de dinero: "))

dinero_1 = dinero_inicial * math.pow(1.04, 1)
dinero_2 = dinero_inicial * math.pow(1.04, 2)
dinero_3 = dinero_inicial * math.pow(1.04, 3)

print("Año 1: " + str(dinero_1))
print("Año 1: " + str(dinero_2))
print("Año 1: " + str(dinero_3))