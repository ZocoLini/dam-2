class Contacto:
    nombre: str
    telefono: str
    email: str

    def __init__(self, nombre, telefono, email):
        self.nombre = nombre
        self.telefono = telefono
        self.email = email

    pass

class Agenda:
    contactos: dict

    def __init__(self):
        self.contactos = dict()

    def agregar_contacto(self, contacto: Contacto):
        self.contactos[contacto.nombre] = contacto

    def listar_contactos(self):
        for contacto in self.contactos.values():
            print(f'Nombre: {contacto.nombre}')
            print(f'Telefono: {contacto.telefono}')
            print(f'Email: {contacto.email}')
            print('------------------------')

    def buscar_contacto(self, nombre):
        contacto = self.contactos[nombre]
        if contacto:
            print(f'Nombre: {contacto.nombre}')
            print(f'Telefono: {contacto.telefono}')
            print(f'Email: {contacto.email}')
        else:
            print('Contacto no encontrado')

    def editar_contacto(self, nombre, nuevo_nombre, nuevo_telefono, nuevo_email):
        contacto = self.contactos[nombre]

        if contacto:
            contacto.nombre = nuevo_nombre
            contacto.telefono = nuevo_telefono
            contacto.email = nuevo_email
        else:
            print('Contacto no encontrado')

    def cerrar_agenda(self):
        self.contactos = dict()

    pass

if __name__ == '__main__':
    agenda = Agenda()

    while 1:
        print('1. Agregar contacto')
        print('2. Listar contactos')
        print('3. Buscar contacto')
        print('4. Editar contacto')
        print('5. Cerrar agenda')
        print('6. Salir')

        opcion = int(input('Opcion: '))

        if opcion == 1:
            nombre = input('Nombre: ')
            telefono = input('Telefono: ')
            email = input('Email: ')
            contacto = Contacto(nombre, telefono, email)
            agenda.agregar_contacto(contacto)
        elif opcion == 2:
            agenda.listar_contactos()
        elif opcion == 3:
            nombre = input('Nombre: ')
            agenda.buscar_contacto(nombre)
        elif opcion == 4:
            nombre = input('Nombre: ')
            nuevo_nombre = input('Nuevo nombre: ')
            nuevo_telefono = input('Nuevo telefono: ')
            nuevo_email = input('Nuevo email: ')
            agenda.editar_contacto(nombre, nuevo_nombre, nuevo_telefono, nuevo_email)
        elif opcion == 5:
            agenda.cerrar_agenda()
        elif opcion == 6:
            break
        else:
            print('Opcion invalida')

        print('------------------------')

    pass

