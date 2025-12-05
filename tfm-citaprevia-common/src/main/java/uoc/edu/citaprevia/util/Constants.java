package uoc.edu.citaprevia.util;

/**
 * Constants globals del projecte CitaPrèvia.
 * La majòria són labels dels fitxers de messages
 */
public final class Constants {

    private Constants() {
    }

    public static final Long CODI_ERROR_FATAL = 500L;
    public static final String MSG_ERR_FATAL = "error.500";
    public static final String PATTERN_FORMAT_LOCAL_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";

    // Errors genèrics de l'API (back-end)
    public static final String ERROR_API_FIND_UBICACIONS          = "error.find.ubicacions";
    public static final String ERROR_API_FIND_HORARIS             = "error.find.horaris";
    public static final String ERROR_API_CRUD_CITA                 = "error.cites.crud";
    public static final String ERROR_API_CRUD_AGENDA               = "error.agendas.crud";
    public static final String ERROR_API_CRUD_TIPUS_CITA           = "error.tipuscita.crud";
    public static final String ERROR_API_CRUD_TECNIC               = "error.tecnics.crud";
    public static final String ERROR_API_CRUD_HORARI               = "error.horaris.crud";
    public static final String ERROR_API_CRUD_UBICACIONS           = "error.ubicacions.crud";
    public static final String ERROR_API_CRUD_SETMANES_TIPUS       = "error.setmanestipus.crud";
    public static final String ERROR_API_CRUD_SUBAPLICACIONS_TIPUS = "error.subaplicacions.crud";
    public static final String MSG_ERR_GET_CALENDARI 			   = "error.get.calendari";   
    public static final String ERROR_API_ADD_CITES_SETMANES_TIPUS       = "error.setmanestipus.cites.add";
    public static final String ERROR_API_UPDATE_CITES_SETMANES_TIPUS    = "error.setmanestipus.cites.update";
    public static final String ERROR_API_CITA_CONCURRENT                = "error.cites.concurrencies";

    // Errors d'operacions específiques d'eliminació de l'api
    public static final String ERROR_API_DELETE_AGENDA                  = "error.agendas.delete";
    public static final String ERROR_API_UPDATE_AGENDA_AMB_CITES        = "error.agendas.cites.update";
    public static final String ERROR_API_DELETE_HORARI                  = "error.horaris.delete";
    public static final String ERROR_API_DELETE_TECNIC_AGENDES          = "error.tecnic.agendes.delete";
    public static final String ERROR_API_DELETE_SETMANES_TIPUS          = "error.setmanestipus.delete";
    public static final String ERROR_API_DELETE_TIPUS_CITES_HORARIS     = "error.tipuscites.horaris.delete";
    public static final String ERROR_API_DELETE_AGENDES_UBICACIONS      = "error.ubicacions.agendes.delete";
    public static final String MSG_ERR_DELETE_CITA 	  			   = "error.delete.cita";


    // Errors genèrics del front (front-end)
    public static final String ERROR_FRONT_GESTIO_TECNICS      = "error.tecnics.gestio";
    public static final String ERROR_FRONT_GESTIO_CITES      = "error.cites.gestio";
    public static final String ERROR_FRONT_DELETE_CITA_PASSADA  = "error.cita.delete.passada";
    public static final String ERROR_FRONT_GESTIO_AGENDES      = "error.agendes.gestio";
    public static final String ERROR_FRONT_GESTIO_HORARIS      = "error.horaris.gestio";
    public static final String ERROR_FRONT_GESTIO_SETMANES_TIPUS = "error.setmanestipus.gestio";
    public static final String ERROR_FRONT_GESTIO_TIPUS_CITES = "error.tipuscites.gestio";
    public static final String ERROR_FRONT_GESTIO_UBICACIONS  = "error.ubicacions.gestio";
    public static final String ERROR_FRONT_SUBAPLICACIO_NO_TROBADA  = "error.subapl.no.trobada";
    public static final String ERROR_FRONT_SAVE_AGENDES       = "error.agendes.save";
    public static final String ERROR_FRONT_SAVE_HORARIS       = "error.horaris.save";
    public static final String ERROR_FRONT_DASHBOARD 		  = "error.dashboard";
    public static final String ERROR_BINDINGS_FORM			  = "error.result.bindings";
    public static final String ERROR_FRONT_PRF_TECNICS        = "error.tecnics.prf"; 

    // Missatges quan una operació és satisfactòria
    // Agendes
    public static final String SUCCESS_FRONT_SAVE_AGENDES      = "success.agendes.save";
    public static final String SUCCESS_FRONT_UPDATE_AGENDES    = "success.agendes.update";
    public static final String SUCCESS_FRONT_DELETE_AGENDES    = "success.agendes.delete";

    // Horaris
    public static final String SUCCESS_FRONT_SAVE_HORARIS      = "success.horaris.save";
    public static final String SUCCESS_FRONT_UPDATE_HORARIS    = "success.horaris.update";
    public static final String SUCCESS_FRONT_DELETE_HORARIS    = "success.horaris.delete";

    // Franges horàries (setmanes tipus)
    public static final String SUCCESS_FRONT_SAVE_SETMANES_TIPUS = "success.setmanestipus.save";
    public static final String SUCCESS_FRONT_DELETE_SETMANES_TIPUS  = "success.setmanestipus.delete";
    
    // Tipus de cites
    public static final String SUCCESS_FRONT_SAVE_TIPUS_CITES    = "success.tipuscites.save";
    public static final String SUCCESS_FRONT_UPDATE_TIPUS_CITES  = "success.tipuscites.update";
    public static final String SUCCESS_FRONT_DELETE_TIPUS_CITES  = "success.tipuscites.delete";

    // Ubicacions
    public static final String SUCCESS_FRONT_SAVE_UBICACIONS     = "success.ubicacions.save";
    public static final String SUCCESS_FRONT_UPDATE_UBICACIONS   = "success.ubicacions.update";
    public static final String SUCCESS_FRONT_DELETE_UBICACIONS   = "success.ubicacions.delete";

    // Tècnics
    public static final String SUCCESS_FRONT_SAVE_TECNICS        = "success.tecnics.save";
    public static final String SUCCESS_FRONT_UPDATE_TECNICS      = "success.tecnics.update";
    public static final String SUCCESS_FRONT_DELETE_TECNICS      = "success.tecnics.delete";
    
    // Cites
    public static final String SUCCESS_FRONT_SAVE_CITES       = "success.cites.save";
    public static final String SUCCESS_FRONT_DELETE_CITES      = "success.cites.delete";
    
}