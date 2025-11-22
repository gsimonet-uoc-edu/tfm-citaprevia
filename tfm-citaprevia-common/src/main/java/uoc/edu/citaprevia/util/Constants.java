package uoc.edu.citaprevia.util;

/**
 * Constants globals del projecte CitaPrèvia.
 * Totes les claus fan referència a entrades de messages_ca.properties / messages_es.properties
 */
public final class Constants {

    private Constants() {
    }

    // ==================================================================
    // ERRORS GENÈRICS I FATAL
    // ==================================================================
    public static final Long CODI_ERROR_FATAL = 500L;
    public static final String MSG_ERR_FATAL = "error.500";
    public static final String MSG_ERR_GET_CALENDARI = "error.get.calendari";
    public static final String MSG_ERR_DELETE_CITA = "error.delete.cita";

    // ==================================================================
    // FORMATOS
    // ==================================================================
    public static final String PATTERN_FORMAT_LOCAL_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";

    // ==================================================================
    // ERRORS API (backend → frontend)
    // ==================================================================
    public static final String ERROR_API_FIND_UBICACIONS          = "error.find.ubicacions";
    public static final String ERROR_API_FIND_HORARIS             = "error.find.horaris";

    public static final String ERROR_API_CRUD_CITA                 = "error.cites.crud";
    public static final String ERROR_API_CRUD_AGENDA               = "error.agendas.crud";
    public static final String ERROR_API_CRUD_TIPUS_CITA           = "error.tipuscita.crud";
    public static final String ERROR_API_CRUD_TECNIC               = "error.tecnics.crud";
    public static final String ERROR_API_CRUD_HORARI               = "error.horaris.crud";
    public static final String ERROR_API_CRUD_UBICACIONS           = "error.ubicacions.crud";
    public static final String ERROR_API_CRUD_SETMANES_TIPUS       = "error.setmanestipus.crud";

    // Operacions específiques d'eliminació (dependències)
    public static final String ERROR_API_DELETE_AGENDA                  = "error.agendas.delete";
    public static final String ERROR_API_DELETE_HORARI                  = "error.horaris.delete";
    public static final String ERROR_API_DELETE_TECNIC_AGENDES          = "error.tecnic.agendes.delete";
    public static final String ERROR_API_DELETE_SETMANES_TIPUS          = "error.setmanestipus.delete";
    public static final String ERROR_API_DELETE_TIPUS_CITES_HORARIS     = "error.tipuscites.horaris.delete";
    public static final String ERROR_API_DELETE_AGENDES_UBICACIONS      = "error.ubicacions.agendes.delete";

    // Operacions amb cites dins franges
    public static final String ERROR_API_ADD_CITES_SETMANES_TIPUS       = "error.setmanestipus.cites.add";
    public static final String ERROR_API_UPDATE_CITES_SETMANES_TIPUS    = "error.setmanestipus.cites.update";

    // ==================================================================
    // ERRORS FRONTEND (missatges mostrats a l'usuari final)
    // ==================================================================
    public static final String ERROR_FRONT_GESTIO_TECNICS      = "error.tecnics.gestio";
    public static final String ERROR_FRONT_GESTIO_AGENDES      = "error.agendes.gestio";
    public static final String ERROR_FRONT_GESTIO_HORARIS      = "error.horaris.gestio";
    public static final String ERROR_FRONT_GESTIO_SETMANES_TIPUS = "error.setmanestipus.gestio";
    public static final String ERROR_FRONT_GESTIO_TIPUS_CITES = "error.tipuscites.gestio";
    public static final String ERROR_FRONT_GESTIO_UBICACIONS  = "error.ubicacions.gestio";

    public static final String ERROR_FRONT_SAVE_AGENDES       = "error.agendes.save";
    public static final String ERROR_FRONT_SAVE_HORARIS       = "error.horaris.save";

    public static final String ERROR_FRONT_PRF_TECNICS        = "error.tecnics.prf";   // Perfil no permès a la subaplicació

    // ==================================================================
    // SUCCESS FRONTEND (missatges d'èxit mostrats a l'usuari)
    // ==================================================================
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
}