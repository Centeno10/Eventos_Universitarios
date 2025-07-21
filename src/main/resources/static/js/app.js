// URL base de la API para eventos
const API_URL = '/api/eventos';

// Cargar eventos cuando la página se carga
document.addEventListener('DOMContentLoaded', function() {
    cargarEventos();
});

// Función para cargar todos los eventos
async function cargarEventos() {
    try {
        const response = await fetch(API_URL);
        const eventos = await response.json();
        
        const tbody = document.getElementById('listaEventos');
        tbody.innerHTML = '';
        
        if (eventos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="9" class="text-center">No hay eventos registrados</td></tr>';
            return;
        }
        
        eventos.forEach(evento => {
            const tr = document.createElement('tr');
            // Formatear la fecha para mostrarla correctamente
            const fechaFormateada = new Date(evento.fecha).toLocaleDateString('es-ES', {
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });

            tr.innerHTML = `
                <td>${evento.id}</td>
                <td>${evento.nombre}</td>
                <td>${fechaFormateada}</td>
                <td>${evento.lugar}</td>
                <td>${evento.descripcion}</td>
                <td>${evento.capacidad}</td>
                <td>${evento.tipoEvento}</td>
                <td>
                    <button class="btn btn-sm btn-secondary" onclick="editarEvento(${evento.id})">Editar</button>
                    <button class="btn btn-sm btn-danger" onclick="eliminarEvento(${evento.id})">Eliminar</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error al cargar eventos:', error);
        mostrarMensaje('Error al cargar los eventos', 'error');
    }
}

// Mostrar formulario para nuevo evento
function mostrarFormulario() {
    document.getElementById('tituloFormulario').textContent = 'Nuevo Evento';
    document.getElementById('formularioEvento').reset();
    document.getElementById('eventoId').value = '';
    const modal = document.getElementById('modalFormulario');
    modal.classList.add('show'); // Añadir clase 'show' para animar
}

// Cerrar formulario
function cerrarFormulario() {
    const modal = document.getElementById('modalFormulario');
    modal.classList.remove('show'); // Quitar clase 'show' para animar
}

// Editar evento
async function editarEvento(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        const evento = await response.json();
        
        document.getElementById('tituloFormulario').textContent = 'Editar Evento';
        document.getElementById('eventoId').value = evento.id;
        document.getElementById('nombre').value = evento.nombre;
        // Formatear la fecha para el input type="date"
        const fechaParaInput = new Date(evento.fecha).toISOString().split('T')[0];
        document.getElementById('fecha').value = fechaParaInput;
        document.getElementById('lugar').value = evento.lugar;
        document.getElementById('descripcion').value = evento.descripcion;
        document.getElementById('capacidad').value = evento.capacidad;
        document.getElementById('tipoEvento').value = evento.tipoEvento;
        
        const modal = document.getElementById('modalFormulario');
        modal.classList.add('show'); // Añadir clase 'show' para animar
    } catch (error) {
        console.error('Error al cargar evento:', error);
        mostrarMensaje('Error al cargar el evento', 'error');
    }
}

// Guardar evento (crear o actualizar)
async function guardarEvento(event) {
    event.preventDefault();
    
    const id = document.getElementById('eventoId').value;
    const evento = {
        nombre: document.getElementById('nombre').value,
        fecha: document.getElementById('fecha').value, // La fecha ya está en formato YYYY-MM-DD
        lugar: document.getElementById('lugar').value,
        descripcion: document.getElementById('descripcion').value,
        capacidad: parseInt(document.getElementById('capacidad').value),
        tipoEvento: document.getElementById('tipoEvento').value
    };
    
    try {
        let response;
        if (id) {
            // Actualizar
            response = await fetch(`${API_URL}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(evento)
            });
        } else {
            // Crear
            response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(evento)
            });
        }
        
        if (response.ok) {
            cerrarFormulario();
            cargarEventos();
            mostrarMensaje('Evento guardado exitosamente', 'success');
        } else {
            throw new Error('Error al guardar');
        }
    } catch (error) {
        console.error('Error al guardar evento:', error);
        mostrarMensaje('Error al guardar el evento', 'error');
    }
}

// Eliminar evento
async function eliminarEvento(id) {
    if (!confirm('¿Está seguro de eliminar este evento?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            cargarEventos();
            mostrarMensaje('Evento eliminado exitosamente', 'success');
        } else {
            throw new Error('Error al eliminar');
        }
    } catch (error) {
        console.error('Error al eliminar evento:', error);
        mostrarMensaje('Error al eliminar el evento', 'error');
    }
}

// Mostrar mensaje
function mostrarMensaje(texto, tipo) {
    const mensaje = document.getElementById('mensaje');
    mensaje.textContent = texto;
    mensaje.className = `alert alert-${tipo}`;
    mensaje.style.display = 'block';
    
    setTimeout(() => {
        mensaje.style.display = 'none';
    }, 3000);
}