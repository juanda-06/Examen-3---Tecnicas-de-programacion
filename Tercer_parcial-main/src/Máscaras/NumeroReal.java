package Máscaras;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;


public class NumeroReal extends DocumentFilter {
@Override
    public void replace(FilterBypass filtro, int offset, int longitud, String caracterDigitado, AttributeSet atributos)
    throws BadLocationException
    {  
        System.out.println(caracterDigitado);
        Document documento = filtro.getDocument();
        String textoActual = documento.getText(0, documento.getLength());

        System.out.println("Carácter digitado = "+ caracterDigitado);
        System.out.println("texto actual = "+ textoActual);
        
        textoActual+= caracterDigitado;

        if(textoActual.matches("[0-9]+[.]?[0-9]*")){

            super.insertString(filtro, offset, caracterDigitado, atributos); 
        }
               

    }

}
