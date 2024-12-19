class Alumno:
    def __init__(self, nombre, nota):
        self.nombre = nombre
        self.nota = nota

    def print_nombre(self):
        print(self.nombre)

    def print_nota(self):
        print(self.nota)

    def mostrar_estado_nota(self):
        print("La nota del alumno es: ", self.nota)
        print("El alumno ha ", "aprobado" if self.nota >= 5 else "suspendido")