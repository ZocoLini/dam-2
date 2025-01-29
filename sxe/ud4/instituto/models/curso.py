from odoo import fields, models, api

class Curso(models.Model):
    _name = "instituto.curso"
    _description = "isntituto curso"

    name = fields.Char(string='Titulo', required=True)
    is_done = fields.Boolean(string='Done')
    description = fields.Text(string = 'Descripción')
    active = fields.Boolean(string='Active')

    def _compute_urgente(self):
        for record in self:
            if record.dias > 7:
                record.abierto = True
            else:
                record.abierto = False
