class Persona:
    def __init__(self, nombre, edad):
        self.nombre = nombre
        self.edad = edad

    def print_nombre(self):
        print(self.nombre)

    def print_edad(self):
        print(self.edad)

    def mostar_mayoria_edad(self):
        print("La persona ", "es mayor de eada" if self.edad >= 18 else "no es mayor de edad", " y tiene ", self.edad, " años")