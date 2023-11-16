package edu.pucmm.eict.practica4.encapsulaciones;

/**
 *
 * @param usuario
 * @param password
 */
public record AuthRequest(String usuario, String password) {
}