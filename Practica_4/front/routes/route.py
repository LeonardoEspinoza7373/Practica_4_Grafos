from flask import Blueprint, render_template, request, redirect, url_for, flash
import json
import requests

router = Blueprint('router', __name__)

# Ruta para el archivo de grafos JSON
GRAFOS_FILE = "C:\\Users\\patri\\OneDrive\\Escritorio\\Practica_4\\back\\data\\grafos.json"
JAVA_API_BASE_URL = "http://localhost:8090/api"
# Funciones auxiliares para manipular el archivo JSON
def cargar_datos(file_path):
    try:
        with open(file_path, 'r') as file:
            return json.load(file)
    except FileNotFoundError:
        return []
    except json.JSONDecodeError:
        return []

def guardar_datos(file_path, data):
    try:
        with open(file_path, 'w') as file:
            json.dump(data, file, indent=4)
    except Exception as e:
        print(f"Error al guardar datos: {e}")

# CRUD para grafos

# Página de inicio
@router.route('/')
def home():
    return render_template('index.html')

# Mostrar lista de grafos
@router.route('/grafo')
def listar_grafos():
    grafos = cargar_datos(GRAFOS_FILE)
    return render_template('ver_grafo.html', grafos=grafos)

# Agregar un nuevo grafo
@router.route('/grafo/agregar', methods=['GET', 'POST'])
def agregar_grafo():
    if request.method == 'POST':
        try:
            grafos = cargar_datos(GRAFOS_FILE)
            nombre = request.form['nombre']
            descripcion = request.form['descripcion']
            paradas_input = request.form['paradas']  # Cambiado de vertices a paradas
            rutas_input = request.form['rutas']      # Cambiado de aristas a rutas

            # Procesar paradas
            paradas = []
            for parada in paradas_input.split(';'):
                nombre_parada, id_parada = parada.split(',')
                paradas.append({'nombre': nombre_parada.strip(), 'id': int(id_parada.strip())})

            # Procesar rutas y vincular con paradas
            rutas = []
            ruta_id = 1
            for ruta in rutas_input.split(';'):
                origen_id, destino_id, distancia = ruta.split(',')
                origen = next(p for p in paradas if p['id'] == int(origen_id.strip()))
                destino = next(p for p in paradas if p['id'] == int(destino_id.strip()))
                rutas.append({
                    'id': ruta_id,
                    'origen': origen,
                    'destino': destino,
                    'distancia': float(distancia.strip())
                })
                ruta_id += 1

            nuevo_grafo = {
                "id": len(grafos) + 1,
                "nombre": nombre,
                "descripcion": descripcion,
                "paradas": paradas,  # Cambiado de vertices a paradas
                "rutas": rutas       # Cambiado de aristas a rutas
            }

            grafos.append(nuevo_grafo)
            guardar_datos(GRAFOS_FILE, grafos)
            flash("Grafo agregado exitosamente", "success")
            return redirect(url_for('router.listar_grafos'))
        except Exception as e:
            flash(f"Error: {e}", "error")
    return render_template('agregar_grafo.html')

# Modificar un grafo
@router.route('/grafo/modificar/<int:id>', methods=['GET', 'POST'])
def modificar_grafo(id):
    grafos = cargar_datos(GRAFOS_FILE)
    grafo = next((g for g in grafos if g['id'] == id), None)

    if not grafo:
        flash("Grafo no encontrado", "error")
        return redirect(url_for('router.listar_grafos'))

    if request.method == 'POST':
        try:
            nombre = request.form['nombre']
            descripcion = request.form['descripcion']
            paradas_input = request.form['vertices']
            rutas_input = request.form['aristas']

            paradas = []
            for vertice in paradas_input.split(';'):
                nombre_parada, id_parada = vertice.split(',')
                paradas.append({'nombre': nombre_parada.strip(), 'id': int(id_parada.strip())})

            rutas = []
            for ruta in rutas_input.split(';'):
                parada1_id, parada2_id, distancia = ruta.split(',')
                rutas.append([int(parada1_id.strip()), int(parada2_id.strip()), float(distancia.strip())])

            grafo.update({
                "nombre": nombre,
                "descripcion": descripcion,
                "paradas": paradas,
                "rutas": rutas
            })

            guardar_datos(GRAFOS_FILE, grafos)
            flash("Grafo actualizado exitosamente", "success")
            return redirect(url_for('router.listar_grafos'))
        except Exception as e:
            flash(f"Error: {e}", "error")
    return render_template('modificar_grafo.html', grafo=grafo)

# Eliminar un grafo
@router.route('/grafo/eliminar/<int:id>', methods=['POST'])
def eliminar_grafo(id):
    try:
        grafos = cargar_datos(GRAFOS_FILE)
        grafos = [g for g in grafos if g['id'] != id]
        guardar_datos(GRAFOS_FILE, grafos)
        flash("Grafo eliminado exitosamente", "success")
    except Exception as e:
        flash(f"Error: {e}", "error")
    return redirect(url_for('router.listar_grafos'))

# Seleccionar y calcular el camino más corto
@router.route('/camino_mas_corto', methods=['GET', 'POST'])
def seleccionar_camino():
    if request.method == 'POST':
        try:
            algoritmo = request.form['algoritmo']
            nodo_inicio = int(request.form['nodo_inicio'])
            nodo_destino = int(request.form['nodo_destino'])

            api_url = f"{JAVA_API_BASE_URL}/grafo/camino/{algoritmo}/{nodo_inicio}/{nodo_destino}"
            
            try:
                # 👇 Timeout de 10 segundos y manejo de errores
                response = requests.get(api_url, timeout=10)
                if response.status_code == 200:
                    resultado = response.json()
                    return render_template('resultado_camino.html', resultado=resultado)
                else:
                    flash(f"Error Java (HTTP {response.status_code}): {response.text}", "error")
            except requests.exceptions.ConnectionError:
                flash("No se pudo conectar a la API Java. Verifica que esté corriendo.", "error")
            except requests.exceptions.Timeout:
                flash("La API Java no respondió a tiempo.", "error")
            except Exception as e:
                flash(f"Error inesperado: {str(e)}", "error")

        except Exception as e:
            flash(f"Error: {str(e)}", "error")

    grafos = cargar_datos(GRAFOS_FILE)
    return render_template('seleccionar_camino.html', grafos=grafos)