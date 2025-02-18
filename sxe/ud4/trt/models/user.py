from odoo import fields, models, api

## def _generate_license_number():
##     random_part = ''.join(random.choices(string.ascii_uppercase + string.digits, k=8))
##     return f"LIC-{random_part}"


class License(models.Model):
    _name = "trt.user"
    _description = "The Round Table user"

    name = fields.Char(string="Email", required=True)
    password = fields.Char(string="Password", required=True)

    licenses = fields.One2many('trt.license', 'user', string="Licenses")

    # @api.model
    # def _get_default_license(self):
    #     license_number = _generate_license_number()

    #     license_vals = {
    #         'name': license_number,
    #         'type': '0',
    #         'validity_date': fields.Date.today(),
    #     }

    #     return [(0, 0, license_vals)]

    # @api.depends('dias')
    # def _compute_urgente(self):
    #     for record in self:
    #         if record.dias > 7:
    #             record.abierto = True
    #         else:
    #             record.abierto = False

    # @api.constraints('licenses')
    # def _check_min_licenses(self):
    #     for record in self:
    #         if record.alias > 7:
    #             raise ValidationError("Validacion para el campo")