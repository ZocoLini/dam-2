from odoo import fields, models

class Destinos(models.Model):
    _name = "viajes.destinos"
    _description = "Información sobre las rutas"

    matricula = fields.One2many('viajes.aeronaves', string="Matricula")
    origen = fields.Char(string="Origen", required=True)
    destino = fields.Char(string="Destino", required=True)
    fecha_vuelo = fields.Datetime(string="Fecha de Vuelo", required=True)
   
