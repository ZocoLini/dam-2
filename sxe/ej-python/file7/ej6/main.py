class Clinte:
    nombre: str
    cantidad: int

    def __init__(self, nombre, cantidad):
        self.nombre = nombre
        self.cantidad = cantidad

    def ingresar(self, banco, cant):
        banco.cantidad += self.cantidad

    def extraer(self, banco, cant):
        banco.cantidad -= self.cantidad

    def mostrar_total(self): pass

class Banco:
    cuntas: dict

    def __init__(self):
        self.cuentas = dict()

    def operar(self, cliente, cantidad): pass

    def deposito_total(self): pass