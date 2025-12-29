import React, { useState } from 'react'
import {
    obtenerPaquetes,
    obtenerPaquetePorId,
    crearPaquete,
    actualizarPaquete,
    eliminarPaquete as eliminarPaqueteServicio,
} from '../services/gestionPaquetes'

const GestionPaquetes = () => {

    const [paquetes, setPaquetes] = useState([])
    const [paquete, setPaquete] = useState(null)

    const [idInput, setIdInput] = useState('')
    const [paqueteIdInput, setPaqueteIdInput] = useState('')

    const [clienteInput, setClienteInput] = useState('')
    const [pesoInput, setPesoInput] = useState('')
    const [destinoInput, setDestinoInput] = useState('')
    const [estadoInput, setEstadoInput] = useState('')
    const [centroInput, setCentroInput] = useState('')

    const [actualizar, setActualizar] = useState(false);
    const [crear, setCrear] = useState(false);
    const [editandoId, setEditandoId] = useState(null);

    const [estadoError, setEstadoError] = useState(null)
    const [estadoSuccess, setEstadoSuccess] = useState(null)

    const limpiarEstados = () => {
        setEstadoError(null)
        setEstadoSuccess(null)
    }

    const mostrarPaquetes = async () => {
        try {
            limpiarEstados()
            const data = await obtenerPaquetes()
            console.log('Paquetes recibidos:', data)
            setPaquetes(data)
            setPaquete(null)
            setEstadoSuccess('Paquetes cargados correctamente')
        } catch {
            setEstadoError('Error al cargar los paquetes')
        }
    }

    const mostrarPaquetePorId = async (id) => {
        try {
            limpiarEstados()
            const data = await obtenerPaquetePorId(id)
            setPaquete(data)
            setPaquetes([])
            setEstadoSuccess('Paquete cargado correctamente')
        } catch {
            setEstadoError('Error al cargar el paquete')
        }
    }

    const crearNuevoPaquete = async (id, cliente, peso, destino, estado, centro) => {
        try {
            limpiarEstados()
            const idTrim = String(id || '').trim()
            const clienteTrim = String(cliente || '').trim()
            const pesoNum = Number(peso)
            const destinoTrim = String(destino || '').trim()
            const estadoNorm = String(estado || '').trim().toUpperCase()
            const centroTrim = String(centro || '').trim()

            if (!idTrim || !clienteTrim || !destinoTrim || !estadoNorm || !centroTrim || Number.isNaN(pesoNum)) {
                setEstadoError('Por favor completa todos los campos con valores válidos')
                return
            }
            if (pesoNum <= 0) {
                setEstadoError('Peso debe ser un número positivo')
                return
            }
            const estadosValidos = ['PENDIENTE', 'EN_TRANSITO', 'ENTREGADO']
            if (!estadosValidos.includes(estadoNorm)) {
                setEstadoError('Estado inválido. Usa PENDIENTE, EN_TRANSITO o ENTREGADO')
                return
            }
            const data = await crearPaquete(idTrim, clienteTrim, pesoNum, destinoTrim, estadoNorm, centroTrim)
            setEstadoSuccess(data?.message || 'Paquete creado correctamente')
            limpiarInputs()
            setCrear(false)
        } catch (error) {
            setEstadoError(error.message || 'Error al crear el paquete')
        }
    }

    const actualizarPaqueteExistente = async (id, cliente, peso, destino, estado, centro) => {
        try {
            limpiarEstados()
            const clienteTrim = String(cliente || '').trim()
            const pesoNum = Number(peso)
            const destinoTrim = String(destino || '').trim()
            const estadoNorm = String(estado || '').trim().toUpperCase()
            const centroTrim = String(centro || '').trim()

            if (!clienteTrim || !destinoTrim || !estadoNorm || !centroTrim || Number.isNaN(pesoNum)) {
                setEstadoError('Por favor completa todos los campos con valores válidos')
                return
            }
            if (pesoNum <= 0) {
                setEstadoError('Peso debe ser un número positivo')
                return
            }
            const estadosValidos = ['PENDIENTE', 'EN_TRANSITO', 'ENTREGADO']
            if (!estadosValidos.includes(estadoNorm)) {
                setEstadoError('Estado inválido. Usa PENDIENTE, EN_TRANSITO o ENTREGADO')
                return
            }
            const data = await actualizarPaquete(id, clienteTrim, pesoNum, destinoTrim, estadoNorm, centroTrim)
            setEstadoSuccess(data?.message || 'Paquete actualizado correctamente')
            setEditandoId(null)
            limpiarInputs()
            if (paquete && paquete.id === id) {
                await mostrarPaquetePorId(id)
            } else {
                await mostrarPaquetes()
            }
        } catch (error) {
            setEstadoError(error.message || 'Error al actualizar el paquete')
        }
    }

    const eliminarPaquete = async (id) => {
        try {
            limpiarEstados()
            await eliminarPaqueteServicio(id)
            setEstadoSuccess('Paquete eliminado correctamente')
        } catch (error) {
            setEstadoError('Error al eliminar el paquete')
        }
    }

    const limpiarInputs = () => {
        setIdInput('')
        setClienteInput('')
        setPesoInput('')
        setDestinoInput('')
        setEstadoInput('')
        setCentroInput('')
    }

    return (
        <div>
            {estadoError && <div className="error">{estadoError}</div>}
            {estadoSuccess && <div className="success">{estadoSuccess}</div>}
            <h2>Gestión de Paquetes</h2>
            <button onClick={() => setCrear(true)}>Crear Nuevo Paquete</button>
            {crear && (
                <div style={{ border: '1px solid #ccc', padding: '15px', marginBottom: '20px' }}>
                    <h3>Crear Nuevo Paquete</h3>
                    <input
                        type="text"
                        placeholder="ID del Paquete *"
                        value={idInput}
                        onChange={(e) => setIdInput(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Cliente *"
                        value={clienteInput}
                        onChange={(e) => setClienteInput(e.target.value)}
                    />
                    <input
                        type="number"
                        placeholder="Peso (kg) *"
                        value={pesoInput}
                        onChange={(e) => setPesoInput(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Destino *"
                        value={destinoInput}
                        onChange={(e) => setDestinoInput(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Estado (PENDIENTE/EN_TRANSITO/ENTREGADO) *"
                        value={estadoInput}
                        onChange={(e) => setEstadoInput(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Centro *"
                        value={centroInput}
                        onChange={(e) => setCentroInput(e.target.value)}
                    />
                    <div style={{ marginTop: '10px' }}>
                        <button onClick={async () => {
                            await crearNuevoPaquete(idInput, clienteInput, pesoInput, destinoInput, estadoInput, centroInput)
                        }}>Crear Paquete</button>
                        <button onClick={() => { setCrear(false); limpiarInputs(); }}>Cancelar</button>
                    </div>
                </div>
            )}
            <h3>Buscar ruta por ID</h3>
            <input
                placeholder="ID del paquete"
                value={paqueteIdInput}
                onChange={(e) => setPaqueteIdInput(e.target.value)}
            />
            <button onClick={() => mostrarPaquetePorId(paqueteIdInput)}>Buscar Paquete</button>
            {paquete && (
                <div className="card">
                    <h3>{paquete.id}</h3>
                    <p>Cliente: {paquete.cliente}</p>
                    <p>Peso: {paquete.peso}</p>
                    <p>Destino: {paquete.destino}</p>
                    <p>Estado: {paquete.estado}</p>
                    <p>Centro: {paquete.centroActual || paquete.centro}</p>
                    <button onClick={() => eliminarPaquete(paquete.id)}>Eliminar Paquete</button>
                    <button onClick={() => {
                        setEditandoId(paquete.id);
                        setClienteInput(paquete.cliente || '');
                        setPesoInput(String(paquete.peso ?? ''));
                        setDestinoInput(paquete.destino || '');
                        setEstadoInput(paquete.estado || '');
                        setCentroInput(paquete.centroActual || paquete.centro || '');
                    }}>Actualizar</button>
                    {editandoId === paquete.id && (
                        <div>
                            <input
                                placeholder="Nuevo cliente"
                                value={clienteInput}
                                onChange={(e) => setClienteInput(e.target.value)}
                            />
                            <input
                                type="number"
                                placeholder="Nuevo peso"
                                value={pesoInput}
                                onChange={(e) => setPesoInput(e.target.value)}
                            />
                            <input
                                placeholder="Nuevo destino"
                                value={destinoInput}
                                onChange={(e) => setDestinoInput(e.target.value)}
                            />
                            <input
                                placeholder="Nuevo estado (PENDIENTE/EN_TRANSITO/ENTREGADO)"
                                value={estadoInput}
                                onChange={(e) => setEstadoInput(e.target.value)}
                            />
                            <input
                                placeholder="Nuevo centro"
                                value={centroInput}
                                onChange={(e) => setCentroInput(e.target.value)}
                            />
                            <button onClick={async () => {
                                await actualizarPaqueteExistente(paquete.id, clienteInput, pesoInput, destinoInput, estadoInput, centroInput)
                            }}>Guardar Cambios</button>
                            <button onClick={() => { setEditandoId(null); limpiarInputs(); }}>Cancelar</button>
                        </div>
                    )}
                </div>
            )}

            <button onClick={mostrarPaquetes}>Cargar Paquetes</button>
            {paquetes.length > 0 && (<ul>
                {paquetes.map((pqt) => (
                    <li key={pqt.id} className="card">
                        <h3>{pqt.id}</h3>
                        <p>Cliente: {pqt.cliente}</p>
                        <p>Peso: {pqt.peso}</p>
                        <p>Destino: {pqt.destino}</p>
                        <p>Estado: {pqt.estado}</p>
                        <p>Centro: {pqt.centroActual}</p>
                        <button onClick={() => eliminarPaquete(pqt.id)}>Eliminar Paquete</button>
                        <button onClick={() => {
                            setEditandoId(pqt.id);
                            setClienteInput(pqt.cliente || '');
                            setPesoInput(String(pqt.peso ?? ''));
                            setDestinoInput(pqt.destino || '');
                            setEstadoInput(pqt.estado || '');
                            setCentroInput(pqt.centro || '');
                        }}>Actualizar</button>
                        {editandoId === pqt.id && (
                            <div>
                                <input
                                    placeholder="Nuevo cliente"
                                    value={clienteInput}
                                    onChange={(e) => setClienteInput(e.target.value)}
                                />
                                <input
                                    placeholder="Nuevo peso"
                                    value={pesoInput}
                                    onChange={(e) => setPesoInput(e.target.value)}
                                />
                                <input
                                    placeholder="Nuevo destino"
                                    value={destinoInput}
                                    onChange={(e) => setDestinoInput(e.target.value)}
                                />
                                <input
                                    placeholder="Nuevo estado"
                                    value={estadoInput}
                                    onChange={(e) => setEstadoInput(e.target.value)}
                                />
                                <input
                                    placeholder="Nuevo centro"
                                    value={centroInput}
                                    onChange={(e) => setCentroInput(e.target.value)}
                                />
                                <button onClick={async () => {
                                    await actualizarPaqueteExistente(pqt.id, clienteInput, pesoInput, destinoInput, estadoInput, centroInput)
                                    setEditandoId(null);
                                    limpiarInputs();
                                }}>Guardar Cambios</button>
                                <button onClick={() => { setEditandoId(null); limpiarInputs(); }}>Cancelar</button>
                            </div>
                        )}
                    </li>
                ))}
            </ul>)}
        </div>
    )
}

export default GestionPaquetes
