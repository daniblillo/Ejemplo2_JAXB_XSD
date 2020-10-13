package operaciones;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import clasesdatos.Ventas.Venta;
import clasesdatos.*;

public class principal {

	public static void main(String[] args) {
		
		visualizarxml();
		insertarventa(30, 10, "16-12-2015");
		modificarventa(10, 11, "03-05-2018");
		
		//eliminaventa(30);
		modificaStock(1);
		visualizarxml();
		}
	
	public static void visualizarxml() {

		System.out.println("------------------------------ ");
		System.out.println("-------VISUALIZAR XML--------- ");
		System.out.println("------------------------------ ");
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

			//Crear un objeto de tipo Unmarshaller para convertir datos XML en un árbol de objetos Java
			Unmarshaller u = jaxbContext.createUnmarshaller();

			//JAXBElement representa a un elemento de un documento XML en este caso a un elemento del documento ventasarticulos.xml
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			//Visualizar
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(jaxbElement, System.out);
			
			//Obtener objetos del jaxbElement
			VentasType miventa = (VentasType) jaxbElement.getValue();

			//Otener todas las ventas
			Ventas vent = miventa.getVentas();

			//Guardar ventas en un array
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			System.out.println("---------------------------- ");
			System.out.println("---VISUALIZAR LOS OBJETOS--- ");
			System.out.println("---------------------------- ");
			
			//Datos del artículo
			DatosArtic miartic = (DatosArtic) miventa.getArticulo();

			System.out.println("Nombre artículo: " + miartic.getDenominacion());
			System.out.println("Codigo artículo: " + miartic.getCodigo());
			System.out.println("Stock artículo: " + miartic.getCodigo());
			System.out.println("Hay " + listaVentas.size() + " ventas.");

			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Venta) listaVentas.get(i);
				System.out.println("Número de venta: " + ve.getNumventa() + ", unidades: " + ve.getUnidades() + ", fecha: " + ve.getFecha());
			}
			
		} catch (JAXBException je) {
			System.out.println(je.getCause());
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
				}
		}
	
	private static void insertarventa(int numeventa, int uni, String fecha) {

		System.out.println("---------------------------- ");
		System.out.println("-------AÑADIR VENTA--------- ");
		System.out.println("---------------------------- ");
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			VentasType miventa = (VentasType) jaxbElement.getValue();

			//Todas las ventas
			Ventas vent = miventa.getVentas();

			//Guardar ventas en un array
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			//Comprobación
			int existe = 0;
			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Venta) listaVentas.get(i);
				if (ve.getNumventa().intValue() == numeventa) {
					existe = 1;
					break;
				}
			}

			if (existe == 0) {
				
				//Crear objeto Ventas
				Ventas.Venta ve = new Ventas.Venta();
				ve.setFecha(fecha);
				ve.setUnidades(uni);
				BigInteger nume = BigInteger.valueOf(numeventa);
				ve.setNumventa(nume);
				
				//Añadir ventas a la lista
				listaVentas.add(ve);

				//Marshaller vuelca la lista al fichero XML
				Marshaller m = jaxbContext.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(jaxbElement, new FileOutputStream("./ventasarticulos.xml"));

				System.out.println("Añadida: " + numeventa);

			} else

				System.out.println("El número de venta ya existe: " + numeventa);

		} catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}
	
	private static void eliminaventa(int numeventas) {

		System.out.println("---------------------------- ");
		System.out.println("-------ELIMINAR VENTA--------- ");
		System.out.println("---------------------------- ");
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			VentasType miventa = (VentasType) jaxbElement.getValue();

			//Obtener todas las ventas
			Ventas vent = miventa.getVentas();

			//Guardar ventas en un array
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			//Comprobación
			int existe = 0;
			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Venta) listaVentas.get(i);
				if (ve.getNumventa().intValue() == numeventas) {
					existe = 1;
					break;
				}
			}

			if (existe == 1) {
				
				for (int i = 0; i < listaVentas.size(); i++) {
					Ventas.Venta ve = (Venta) listaVentas.get(i);
					if (ve.getNumventa().intValue() == numeventas) {
						listaVentas.remove(i);
						break;
					}
				}

				//Marshaller vuelca la lista al fichero XML
				Marshaller m = jaxbContext.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(jaxbElement, new FileOutputStream("./ventasarticulos.xml"));

				System.out.println("Venta elimnada: " + numeventas);

			} else

				System.out.println("El número de venta ya existe: " + numeventas);

		} catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}
	
	private static void modificaStock(int stock) {

		System.out.println("---------------------------- ");
		System.out.println("-------MODIFICAR STOCK--------- ");
		System.out.println("---------------------------- ");
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			VentasType miventa = (VentasType) jaxbElement.getValue();
			Ventas vent = miventa.getVentas();
			
			//Obtener todas las ventas
			DatosArtic art =(DatosArtic) miventa.getArticulo();
			art.setStock(art.getStock().add(BigInteger.valueOf(stock)));

			Marshaller m = jaxbContext.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(jaxbElement, new FileOutputStream("./ventasarticulos.xml"));

			System.out.println("Stock: " + art.getCodigo() + ". Se ha incrementado a: " + art.getStock());

			}  catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}
	
	private static void modificarventa(int numeventa, int uni, String fecha) {

		System.out.println("---------------------------- ");
		System.out.println("-------MODIFICAR VENTA--------- ");
		System.out.println("---------------------------- ");
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			JAXBElement jaxbElement = (JAXBElement) u.unmarshal(new FileInputStream("./ventasarticulos.xml"));

			VentasType miventa = (VentasType) jaxbElement.getValue();

			//Obtener las ventas
			Ventas vent = miventa.getVentas();

			//Guardar en un array
			List listaVentas = new ArrayList();
			listaVentas = vent.getVenta();

			//Comprobación
			int existe = 0;
			for (int i = 0; i < listaVentas.size(); i++) {
				Ventas.Venta ve = (Venta) listaVentas.get(i);
				if (ve.getNumventa().intValue() == numeventa) {
					existe = 1;
					ve.setFecha(fecha);
					ve.setUnidades(uni);
					
					//Marshaller vuelca la lista al fichero XML
					Marshaller m = jaxbContext.createMarshaller();
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					m.marshal(jaxbElement, new FileOutputStream("./ventasarticulos.xml"));

					System.out.println("Venta modificada.");
					System.out.println("Número de venta: " + ve.getNumventa() + ", unidades: " + ve.getUnidades() + ", fecha: " + ve.getFecha());
		
					break;
				}
			}

			if (existe == 0) {
				System.out.println("El número " + numeventa + " no existe.");
			}

		} catch (JAXBException je) {
			System.out.println(je.getCause());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

	}
	}