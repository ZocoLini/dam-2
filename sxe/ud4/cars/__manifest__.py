{
    "name": "Cars",  # Module title
    "summary": "Herramienta para gestionar coches",  # Module subtitle phrase
    "description": "Description",  # Supports reStructuredText(RST) format (description is Deprecated)
    "version": "17.0.1.0.0",
    "author": "Borja Castellano Extremaoduro",
    "category": "Tools",
    "website": "https://www.lebastudios.org",
    "license": "AGPL-3",
    "depends": ["base"],
    "data": [
    	"security/cars_security.xml",
        "security/ir.model.access.csv",
        "views/vehiculo.xml",
	],
    "installable": True,
}