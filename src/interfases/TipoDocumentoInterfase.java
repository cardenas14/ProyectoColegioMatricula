/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfases;

import java.util.List;
import modelos.TipoDocumento;

/**
 *
 * @author Novoa
 */
public interface TipoDocumentoInterfase {
    
    List<TipoDocumento> index();
    
    TipoDocumento findId(String id);
}
