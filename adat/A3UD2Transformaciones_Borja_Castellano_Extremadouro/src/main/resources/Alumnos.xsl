<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : Alumnos.xsl
    Author     : maria jose galan
  
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes" />

    <xsl:key name="idAlumno" match="alumno" use="nota"/>
    <xsl:template match="/alumnos">
        <xsl:element name="notas">       
            <xsl:for-each select="alumno[generate-id(.)=generate-id(key('idAlumno',nota))]" >
                <xsl:sort select="nota" order="descending"></xsl:sort>
                <xsl:element name="nota">
                    <xsl:attribute name="valor">
                        <xsl:value-of select="nota"></xsl:value-of>
                    </xsl:attribute>
                    <xsl:for-each select="key('idAlumno',nota)">
                        <xsl:element name="alumno">
                            <xsl:value-of select="nome"></xsl:value-of>
                        </xsl:element>
                    </xsl:for-each>   
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>

