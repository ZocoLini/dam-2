<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes"  encoding="UTF-8"/>

    <xsl:template match="/Hardware">
        <xsl:element name="Hardware">
            <xsl:apply-templates select="Maquina" />
        </xsl:element>
    </xsl:template>

    <xsl:template match="Maquina">
        <xsl:if test="Notas">
            <xsl:comment>
                <xsl:value-of select="Notas" />
            </xsl:comment>
        </xsl:if>
        <xsl:element name="Maquina">
            <xsl:attribute name="fabr">
                <xsl:value-of select="Info/Fabricante" />
            </xsl:attribute>
        </xsl:element>

        <xsl:element name="Nome">
            <xsl:value-of select="@nome" />
            <xsl:text> (</xsl:text>
            <xsl:value-of select="Info/Tipo" />
            <xsl:text>)</xsl:text>
        </xsl:element>

        <xsl:element name="Capacidade">
            <xsl:attribute name="tecnoloxia">
                <xsl:value-of select="Info/Disco/@tecnoloxia" />
            </xsl:attribute>
            <xsl:value-of select="sum(Info/Disco/@capacidade)" />
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
