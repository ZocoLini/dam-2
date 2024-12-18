class Cuenta:
    titular: str
    cantidad: float

    def __init__(self, titular: str = "", cantidad: float = 0.0):
        self.titular = titular
        self.cantidad = cantidad

    def imprimir_datos(self):
        print(f"Titular: {self.titular}")
        print(f"Cantidad: {self.cantidad}")

class PlazoFijo(Cuenta):
    plazo: int
    interes: float

    def __init__(self, titular: str = "", cantidad: float = 0.0, plazo: int = 0, interes: float = 0.0):
        super().__init__(titular, cantidad)
        self.plazo = plazo
        self.interes = interes

    def obtener_importe_interes(self):
        return self.cantidad * self.interes

    def imprimir_informacion(self):
        self.imprimir_datos()
        print(f"Plazo: {self.plazo}")
        print(f"Interes: {self.interes}")
        print(f"Importe interes: {self.obtener_importe_interes()}")

class CajaAhorro(Cuenta):
    def __init__(self, titular: str = "", cantidad: float = 0.0):
        super().__init__(titular, cantidad)

if __name__ == '__main__':
    plazo_fijo = PlazoFijo("pepe", 1000, 30, 0.1)

    plazo_fijo.imprimir_informacion()

    caja_ahorro = CajaAhorro("juan", 500)

    caja_ahorro.imprimir_datos()