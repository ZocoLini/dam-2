class Triangualo:
    def __init__(self, lado1, lado2, lado3):
        self.lado1 = lado1
        self.lado2 = lado2
        self.lado3 = lado3

    def tipo_segun_lados(self):
        if self.lado1 == self.lado2 == self.lado3:
            return "Equilatero"
        elif self.lado1 == self.lado2 or self.lado1 == self.lado3 or self.lado2 == self.lado3:
            return "Isosceles"
        else:
            return "Escaleno"


if __name__ == '__main__':
    triangulo = Triangualo(3, 3, 3)
    print(triangulo.tipo_segun_lados())