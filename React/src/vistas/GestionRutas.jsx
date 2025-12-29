import React, { useState } from 'react'
import {
    obtenerRutas,
    obtenerRutaPorId,
    crearRuta,
    actualizarRuta,
    eliminarRuta as eliminarRutaServicio,
} from '../services/gestionRutas'

const GestionRutas = () => {

    const [estadoError, setEstadoError] = useState(null)
    const [estadoSuccess, setEstadoSuccess] = useState(null)

    const [rutas, setRutas] = useState([])
    const [ruta, setRuta] = useState(null)

    const [rutaIdInput, setRutaIdInput] = useState('')

    const [idInput, setIdInput] = useState('')
    const [origenInput, setOrigenInput] = useState('')
    const [destinoInput, setDestinoInput] = useState('')
    const [distanciaInput, setDistanciaInput] = useState('')

    const [actualizar, setActualizar] = useState(false);
    const [crear, setCrear] = useState(false);
    const [editandoId, setEditandoId] = useState(null);

    const mostrarRutas = async () => {
        try {
            limpiarEstados()
            const data = await obtenerRutas()
            console.log('Rutas recibidas:', data)
            setRutas(data)
            setRuta(null)
            setEstadoSuccess('Rutas cargadas correctamente')
        } catch {
            setEstadoError('Error al cargar las rutas')
        }
    }

    const mostrarRutaPorId = async (id) => {
        try {
            limpiarEstados()
            const data = await obtenerRutaPorId(id)
            setRuta(data)
            setRutas([])
            setEstadoSuccess('Ruta cargada correctamente')
        } catch {
            setEstadoError('Error al cargar la ruta')
        }
    }

    const crearNuevaRuta = async (id, origen, destino, distancia) => {
        try {
            limpiarEstados()
            const data = await crearRuta(id, origen, destino, distancia)
            setEstadoSuccess('Ruta creada correctamente')
            setIdInput('')
            setOrigenInput('')
            setDestinoInput('')
            setDistanciaInput('')
            setCrear(false)
            return data
        } catch (error) {
            setEstadoError('Error al crear la ruta')
            throw error
        }
    }

    const actualizarRutaExistente = async (id, origen, destino, distancia) => {
        try {
            limpiarEstados()
            const data = await actualizarRuta(id, origen, destino, distancia)
            setEstadoSuccess('Ruta actualizada correctamente')
            return data
        } catch (error) {
            setEstadoError('Error al actualizar la ruta')
            throw error
        }
    }

    const eliminarRuta = async (id) => {
        try {
            limpiarEstados()
            await eliminarRutaServicio(id)
            setEstadoSuccess('Ruta eliminada correctamente')
        } catch (error) {
            setEstadoError('Error al eliminar la ruta')
        }
    }

    const limpiarEstados = () => {
        setEstadoError(null)
        setEstadoSuccess(null)
    }

    const limpiarInputs = () => {
        setIdInput('')
        setOrigenInput('')
        setDestinoInput('')
        setDistanciaInput('')
    }

    return (
        <div>
            {estadoError && <p style={{ color: 'red' }}>{estadoError}</p>}
            {estadoSuccess && <p style={{ color: 'green' }}>{estadoSuccess}</p>}
            <h1>Gestión de Rutas</h1>
            <button onClick={() => setCrear(true)}>Crear Nueva Ruta</button>
            {crear && (
                <div>
                    <input
                        placeholder="Id"
                        value={idInput}
                        onChange={(e) => setIdInput(e.target.value)}
                    />
                    <input
                        placeholder="Origen"
                        value={origenInput}
                        onChange={(e) => setOrigenInput(e.target.value)}
                    />
                    <input
                        placeholder="Nuevo destino"
                        value={destinoInput}
                        onChange={(e) => setDestinoInput(e.target.value)}
                    />
                    <input
                        placeholder="Nueva distancia"
                        value={distanciaInput}
                        onChange={(e) => setDistanciaInput(e.target.value)}
                    />
                    <button onClick={async () => {
                        await crearNuevaRuta(idInput, origenInput, destinoInput, distanciaInput)
                    }}>Crear Ruta</button>
                </div>
            )}
            <button onClick={mostrarRutas}>Cargar Rutas</button>

            <h3>Buscar ruta por ID</h3>
            <input
                placeholder="ID de la ruta"
                value={rutaIdInput}
                onChange={(e) => setRutaIdInput(e.target.value)}
            />
            <button onClick={() => mostrarRutaPorId(rutaIdInput)}>Buscar Ruta</button>

            {rutas.length > 0 && (
                <ul>
                    {rutas.map((r) => (
                        <li key={r.id} className="card">
                            <h3>{r.id}</h3>
                            <p>Origen: {r.origen}</p>
                            <p>Destino: {r.destino}</p>
                            <p>Distancia: {r.distancia} km</p>
                            <button onClick={() => eliminarRuta(r.id)}>Eliminar Ruta</button>
                            <button onClick={() => {
                                setEditandoId(r.id);
                                setOrigenInput(r.origen || '');
                                setDestinoInput(r.destino || '');
                                setDistanciaInput(String(r.distancia ?? ''));
                            }}>Actualizar</button>
                            {editandoId === r.id && (
                                <div>
                                    <input
                                        placeholder="Nuevo origen"
                                        value={origenInput}
                                        onChange={(e) => setOrigenInput(e.target.value)}
                                    />
                                    <input
                                        placeholder="Nuevo destino"
                                        value={destinoInput}
                                        onChange={(e) => setDestinoInput(e.target.value)}
                                    />
                                    <input
                                        placeholder="Nueva distancia"
                                        value={distanciaInput}
                                        onChange={(e) => setDistanciaInput(e.target.value)}
                                    />
                                    <button onClick={async () => {
                                        await actualizarRutaExistente(r.id, origenInput, destinoInput, distanciaInput)
                                        setEditandoId(null);
                                        limpiarInputs();
                                    }}>Guardar Cambios</button>
                                    <button onClick={() => { setEditandoId(null); limpiarInputs(); }}>Cancelar</button>
                                </div>
                            )}
                        </li>
                    ))}
                </ul>)}
            {ruta && (
                <div className="card">
                    <h3>{ruta.id}</h3>
                    <p>Origen: {ruta.origen}</p>
                    <p>Destino: {ruta.destino}</p>
                    <p>Distancia: {ruta.distancia} km</p>
                    <button onClick={() => eliminarRuta(ruta.id)}>Eliminar Ruta</button>
                    <button onClick={() => setActualizar(true)}>Actualizar Distancia</button>
                    {actualizar && (
                        <div>
                            <input
                                placeholder="Nuevo origen"
                                value={origenInput}
                                onChange={(e) => setOrigenInput(e.target.value)}
                            />
                            <input
                                placeholder="Nuevo destino"
                                value={destinoInput}
                                onChange={(e) => setDestinoInput(e.target.value)}
                            />
                            <input
                                placeholder="Nueva distancia"
                                value={distanciaInput}
                                onChange={(e) => setDistanciaInput(e.target.value)}
                            />
                            <button onClick={async () => {
                                await actualizarRutaExistente(ruta.id, origenInput, destinoInput, distanciaInput)
                                setActualizar(false);
                                limpiarInputs();
                                mostrarRutaPorId(ruta.id);
                            }}>Guardar Cambios</button>
                        </div>
                    )}
                </div>
            )}
        </div>
    )
}

export default GestionRutas
