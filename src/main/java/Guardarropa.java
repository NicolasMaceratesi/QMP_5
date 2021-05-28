import java.util.ArrayList;
import java.util.List;

public class Usuario {
  List<Guardarropa> guardarropas;

  public void agregarGuardarropa(Guardarropa guardarropa) {
    guardarropas.add(guardarropa);
  }

  public void pedirAalguienQueAgregueUnaPrendaAunGuardarropa(Usuario otroUsuario, Guardarropa unGuardarropa, Prenda unaPrenda) { ;
    unGuardarropa.modificador.agregarPrenda(otroUsuario, unaPrenda);
  }

  public void pedirAalguienQueElimineUnaPrendaAunGuardarropa(Usuario otroUsuario, Guardarropa unGuardarropa, Prenda unaPrenda) { ;
    unGuardarropa.modificador.eliminarPrenda(otroUsuario, unaPrenda);
  }
}

public class Guardarropa {
  CriterioRopa criterio;
  ModificadorGuardarropa modificador;
  List<Prenda> prendas;

  public Guardarropa(CriterioRopa criterio){
    this.criterio = criterio;
    this.modificador = new ModificadorGuardarropa();
  }

  public void agregarPrenda(Prenda prenda){
    if(criterio.cumple(prenda))
      prendas.add(prenda);
  }

  public List<Propuesta> verPropuestasDeModificacion(){
    return modificador.getPropuestas();
  }

  public void rechazarPropuesta(Propuesta propuesta){
    modificador.rechazarPropuesta(propuesta);
  }

  public void aceptarPropuesta(Propuesta propuesta){
    modificador.aceptarPropuesta(propuesta, prendas);
  }

  public void deshacerPropuestas(){
    modificador.deshacerPropuestas(prendas);
  }
}

public class ModificadorGuardarropa{
  List<Propuesta> propuestas;
  List<Propuesta> propuestasAceptadas;

  public ModificadorGuardarropa() {
    this.propuestas = new ArrayList<Propuesta>;
  }

  public void agregarPrenda(Usuario usuario, Prenda prenda){
    Propuesta propuesta = new Propuesta(usuario, prenda, Accion.AGREGAR);
    propuestas.add(prenda);
  }

  public void eliminarPrenda(Usuario usuario, Prenda prenda){
    Propuesta propuesta = new Propuesta(usuario, prenda, Accion.QUITAR);
    propuestas.add(prenda);
  }

  public void rechazarPropuesta(Propuesta propuesta){
    propuestas.remove(propuesta);
  }

  public void aceptarPropuesta(Propuesta propuesta, List<Prenda> prendas){
    propuesta.aceptar(prendas);
    propuestasAceptadas.add(propuesta);
    propuestas.remove(propuesta);
  }

  public void deshacerPropuestas(List<Prenda> prendas){
    propuestasAceptadas.forEach((propuesta -> propuesta.cambiarAaccionContraria().aceptar(prendas)));
  }
}

public class Propuesta{
  Usuario usuario;
  Prenda prenda;
  Accion accion;

  public Propuesta(Usuario usuario, Prenda prenda, Accion accion) {
    this.usuario = usuario;
    this.prenda = prenda;
    this.accion = accion;
  }

  public void aceptar(List<Prenda> prendas){
    accion.efecto(prendas, prenda);
  }

  public void cambiarAaccionContraria(){
    this.accion = this.accion.accionContraria();
  }
}

public enum Accion{
  AGREGAR{
    public void efecto(List<Prenda> prendas, Prenda prendaPropuesta){
      prendas.add(prendaPropuesta);
    }

    public Accion accionContraria(){
      return Accion.QUITAR;
    }
  },
  QUITAR{
    public void efecto(List<Prenda> prendas, Prenda prendaPropuesta){
      prendas.remove(prendaPropuesta);
    }

    public Accion accionContraria(){
      return Accion.AGREGAR;
    }
  }
}

public class Prenda{
}
