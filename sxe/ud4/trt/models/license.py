from odoo import fields, models, api


class License(models.Model):
    _name = "trt.license"
    _description = "Users Licenses and the plugins purchased"

    name = fields.Char(string="Number", required=True)
    # Monthly: Default type, pays monthly based on plugins price
    # Life-Time: Pays once the license and can use any plugin for life
    # Student: Special type for students, free to use any plugin but requires verification and renewal every year
    type = fields.Selection([{'0', 'Monthly'}, {'1', 'LifeTime'}, {'2', 'Student'}], string="Type", default='0', required=True)

    user = fields.Many2one('trt.user', string="User", required=True)
    plugins = fields.Many2many('trt.plugin', string="Plugins")
