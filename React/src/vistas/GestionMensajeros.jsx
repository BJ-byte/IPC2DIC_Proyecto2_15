import React from 'react'
import {
    obtenerMensajeros,
    obtenerMensajeroPorId,
    crearMensajero,
    cambiarEstadoMensajero,
    reasignarMensajero,
} from '../services/gestionMensajeros'

const GestionMensajeros = () => {

    const [mensajeros, setMensajeros] = React.useState([])
    const [mensajero, setMensajero] = React.useState(null)

    const [estadoError, setEstadoError] = React.useState(null)
    const [estadoSuccess, setEstadoSuccess] = React.useState(null)

    const [mensajeroCentroInput, setMensajeroCentroInput] = React.useState('')
    const [mensajeroEstadoInput, setMensajeroEstadoInput] = React.useState('')

    const [idInput, setIdInput] = React.useState('')
    const [nombreInput, setNombreInput] = React.useState('')
    const [capacidadInput, setCapacidadInput] = React.useState('')

    const [crear, setCrear] = React.useState(false);
    const [editandoCentroId, setEditandoCentroId] = React.useState(null);
    const [editandoEstadoId, setEditandoEstadoId] = React.useState(null);

    const limpiarEstados = () => {
        setEstadoError(null);
        setEstadoSuccess(null);
    }

    const limpiarInputs = () => {
        setIdInput('')
        setNombreInput('')
        setCapacidadInput('')
        setMensajeroCentroInput('')
        setMensajeroEstadoInput('')
    }

    const mostrarMensajeros = async () => {
        try {
            limpiarEstados()
            const data = await obtenerMensajeros()
            console.log('Mensajeros recibidos:', data)
            setMensajeros(data)
            setMensajero(null)
            setEstadoSuccess('Mensajeros cargados correctamente')
        } catch {
            setEstadoError('Error al cargar los mensajeros')
        }
    }

    const mostrarMensajeroPorId = async (id) => {
        try {
            limpiarEstados()
            const data = await obtenerMensajeroPorId(id)
            setMensajero(data)
            setMensajeros([])
            setEstadoSuccess('Mensajero cargado correctamente')
        } catch {
            setEstadoError('Error al cargar el mensajero')
        }
    }

    const crearNuevoMensajero = async (id, nombre, capacidad, centro, estado) => {
        try {
            limpiarEstados()
            const idTrim = String(id || '').trim()
            const nombreTrim = String(nombre || '').trim()
            const centroTrim = String(centro || '').trim()
            const estadoNorm = String(estado || '').trim().toUpperCase()
            const capacidadNum = Number(capacidad)

            if (!idTrim || !nombreTrim || !centroTrim || !estadoNorm || Number.isNaN(capacidadNum)) {
                setEstadoError('Por favor completa todos los campos con valores válidos')
                return
            }
            if (capacidadNum <= 0) {
                setEstadoError('Capacidad debe ser un número positivo')
                return
            }
            const estadosValidos = ['DISPONIBLE', 'EN_TRANSITO']
            if (!estadosValidos.includes(estadoNorm)) {
                setEstadoError('Estado inválido. Usa DISPO NIBLE o EN_TRANSITO')
                return
            }
            const data = await crearMensajero(idTrim, nombreTrim, capacidadNum, centroTrim, estadoNorm)
            setEstadoSuccess(data?.message || 'Mensajero creado correctamente')
            limpiarInputs()
            setCrear(false)
        } catch (error) {
            setEstadoError(error.message || 'Error al crear el mensajero')
        }
    }

    const actualizarCentroMensajero = async (id, nuevoCentro) => {
        try {
            limpiarEstados()
            const centroTrim = String(nuevoCentro || '').trim()
            if (!centroTrim) {
                setEstadoError('Centro no puede estar vacío')
                return
            }
            const data = await reasignarMensajero(id, centroTrim)
            setEstadoSuccess(data?.message || 'Centro del mensajero actualizado correctamente')
            setMensajeroCentroInput('')
            setEditandoCentroId(null)
            if (mensajero && mensajero.id === id) {
                await mostrarMensajeroPorId(id)
            } else {
                await mostrarMensajeros()
            }
        } catch (error) {
            setEstadoError(error.message || 'Error al actualizar el centro del mensajero')
        }
    }

    const actualizarEstadoMensajero = async (id, nuevoEstado) => {
        try {
            limpiarEstados()
            const estadoNorm = String(nuevoEstado || '').trim().toUpperCase()
            const estadosValidos = ['DISPONIBLE', 'EN_TRANSITO']
            if (!estadosValidos.includes(estadoNorm)) {
                setEstadoError('Estado inválido. Usa DISPO NIBLE o EN_TRANSITO')
                return
            }
            const data = await cambiarEstadoMensajero(id, estadoNorm)
            setEstadoSuccess(data?.message || 'Estado del mensajero actualizado correctamente')
            setMensajeroEstadoInput('')
            setEditandoEstadoId(null)
            if (mensajero && mensajero.id === id) {
                await mostrarMensajeroPorId(id)
            } else {
                await mostrarMensajeros()
            }
        } catch (error) {
            setEstadoError(error.message || 'Error al actualizar el estado del mensajero')
        }
    }

    return (
        <div>
            <h1>Gestión de Mensajeros</h1>
            {estadoError && <p style={{ color: 'red' }}>{estadoError}</p>}
            {estadoSuccess && <p style={{ color: 'green' }}>{estadoSuccess}</p>}
            <button onClick={() => setCrear(true)}>Crear Mensajero</button>
            {crear && (
                <div>
                    <input
                        placeholder="Id"
                        value={idInput}
                        onChange={(e) => setIdInput(e.target.value)}
                    />
                    <input
                        placeholder="Nombre"
                        value={nombreInput}
                        onChange={(e) => setNombreInput(e.target.value)}
                    />
                    <input
                        placeholder="Capacidad"
                        value={capacidadInput}
                        onChange={(e) => setCapacidadInput(e.target.value)}
                    />
                    <input
                        placeholder="Centro"
                        value={mensajeroCentroInput}
                        onChange={(e) => setMensajeroCentroInput(e.target.value)}
                    />
                    <input
                        placeholder="Estado (DISPONIBLE/EN_TRANSITO)"
                        value={mensajeroEstadoInput}
                        onChange={(e) => setMensajeroEstadoInput(e.target.value)}
                    />
                    <button onClick={async () => {
                        await crearNuevoMensajero(idInput, nombreInput, capacidadInput, mensajeroCentroInput, mensajeroEstadoInput)
                        setCrear(false)
                    }}>Crear Mensajero</button>
                    <button onClick={() => { setCrear(false); limpiarInputs(); }}>Cancelar</button>
                </div>
            )}
            <button onClick={mostrarMensajeros}>Listar Mensajeros</button>
            {mensajeros.length > 0 && (
                <ul>
                    {mensajeros.map((m) => (
                        <li key={m.id} className="card">
                            <h3>{m.nombre}</h3>
                            <p>ID: {m.id}</p>
                            <p>Capacidad: {m.capacidad}</p>
                            <p>Centro: {m.centro}</p>
                            <p>Estado: {m.estadoOperativo}</p>
                            <button onClick={() => {
                                setEditandoCentroId(m.id);
                                setMensajeroCentroInput(m.centro || '');
                            }}>Editar Centro</button>
                            {editandoCentroId === m.id && (
                                <div>
                                    <input
                                        placeholder="Nuevo centro"
                                        value={mensajeroCentroInput}
                                        onChange={(e) => setMensajeroCentroInput(e.target.value)}
                                    />
                                    <button onClick={() => actualizarCentroMensajero(m.id, mensajeroCentroInput)}>Actualizar Centro</button>
                                    <button onClick={() => { setEditandoCentroId(null); setMensajeroCentroInput(''); }}>Cancelar</button>
                                </div>
                            )}
                            <button onClick={() => {
                                setEditandoEstadoId(m.id);
                                setMensajeroEstadoInput(m.estado || '');
                            }}>Editar Estado</button>
                            {editandoEstadoId === m.id && (
                                <div>
                                    <input
                                        placeholder="Nuevo estado (DISPONIBLE/EN_TRANSITO)"
                                        value={mensajeroEstadoInput}
                                        onChange={(e) => setMensajeroEstadoInput(e.target.value)}
                                    />
                                    <button onClick={() => actualizarEstadoMensajero(m.id, mensajeroEstadoInput)}>Actualizar Estado</button>
                                    <button onClick={() => { setEditandoEstadoId(null); setMensajeroEstadoInput(''); }}>Cancelar</button>
                                </div>
                            )}
                        </li>
                    ))}
                </ul>
            )}
            <h3>Buscar mensajero por ID</h3>
            <input
                placeholder="ID del mensajero"
                value={idInput}
                onChange={(e) => setIdInput(e.target.value)}
            />
            <button onClick={() => mostrarMensajeroPorId(idInput)}>Buscar Mensajero</button>
            {mensajero && (
                <div className="card">
                    <h3>{mensajero.nombre}</h3>
                    <p>ID: {mensajero.id}</p>
                    <p>Capacidad: {mensajero.capacidad}</p>
                    <p>Centro: {mensajero.centro}</p>
                    <p>Estado: {mensajero.estadoOperativo}</p>
                    <button onClick={() => {
                        setEditandoCentroId(mensajero.id);
                        setMensajeroCentroInput(mensajero.centro || '');
                    }}>Editar Centro</button>
                    {editandoCentroId === mensajero.id && (
                        <div>
                            <input
                                placeholder="Nuevo centro"
                                value={mensajeroCentroInput}
                                onChange={(e) => setMensajeroCentroInput(e.target.value)}
                            />
                            <button onClick={() => actualizarCentroMensajero(mensajero.id, mensajeroCentroInput)}>Actualizar Centro</button>
                            <button onClick={() => { setEditandoCentroId(null); setMensajeroCentroInput(''); }}>Cancelar</button>
                        </div>
                    )}
                    <button onClick={() => {
                        setEditandoEstadoId(mensajero.id);
                        setMensajeroEstadoInput(mensajero.estado || '');
                    }}>Editar Estado</button>
                    {editandoEstadoId === mensajero.id && (
                        <div>
                            <input
                                placeholder="Nuevo estado (DISPONIBLE/EN_TRANSITO)"
                                value={mensajeroEstadoInput}
                                onChange={(e) => setMensajeroEstadoInput(e.target.value)}
                            />
                            <button onClick={() => actualizarEstadoMensajero(mensajero.id, mensajeroEstadoInput)}>Actualizar Estado</button>
                            <button onClick={() => { setEditandoEstadoId(null); setMensajeroEstadoInput(''); }}>Cancelar</button>
                        </div>
                    )}
                </div>
            )}
        </div>
    )
}

export default GestionMensajeros
