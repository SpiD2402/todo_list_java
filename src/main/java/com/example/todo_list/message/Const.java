package com.example.todo_list.message;

import org.springframework.http.HttpStatus;

public class Const {

    // Códigos de estado HTTP
    public static final int STATUS_OK = 200;
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_NO_CONTENT = 204;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_INTERNAL_ERROR = 500;
    // Mensajes de éxito
    public static final String MSG_SUCCESS = "Operación exitosa";
    public static final String MSG_CREATED = "Recurso creado exitosamente";
    public static final String MSG_NO_CONTENT = "No hay contenido para mostrar";

    // Mensajes de error
    public static final String MSG_BAD_REQUEST = "Solicitud inválida";
    public static final String MSG_UNAUTHORIZED = "No autorizado";
    public static final String MSG_FORBIDDEN = "Acceso prohibido";
    public static final String MSG_NOT_FOUND = "Recurso no encontrado";
    public static final String MSG_INTERNAL_ERROR = "Error interno del servidor";

    // Mensajes específicos de la aplicación
    public static final String MSG_USER_CREATED = "Usuario creado exitosamente";
    public static final String MSG_USER_UPDATED = "Usuario actualizado exitosamente";
    public static final String MSG_USER_DELETED = "Usuario eliminado exitosamente";
    public static final String MSG_USER_NOT_FOUND = "Usuario no encontrado";

    public static final String MSG_INVALID_INPUT = "Entrada inválida";
    public static final String MSG_MISSING_PARAMETER = "Parámetro faltante";
    public static final String MSG_DUPLICATE_ENTRY = "Entrada duplicada";

    public static final String MSG_DATABASE_ERROR = "Error de base de datos";
    public static final String MSG_NETWORK_ERROR = "Error de red";

}
