class Calculadora:
    def __init__(self, num1, num2):
        self.num1 = num1
        self.num2 = num2

    def suma(self):
        return self.num1 + self.num2

    def resta(self):
        return self.num1 - self.num2

    def multiplicacion(self):
        return self.num1 * self.num2

    def division(self):
        return self.num1 / self.num2


if __name__ == '__main__':
    c = Calculadora(2, 3)
    print(c.suma())
    print(c.resta())
    print(c.multiplicacion())
    print(c.division())