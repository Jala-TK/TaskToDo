package com.krodrigues.models.entities;

/**
 * Enumeração que representa os possíveis status de uma tarefa.
 */
public enum StatusTarefa {
    /**
     * Indica que a tarefa ainda não foi iniciada.
     */
    NAO_INICIADA,

    /**
     * Indica que a tarefa está em andamento.
     */
    EM_ANDAMENTO,

    /**
     * Indica que a tarefa está sendo verificada.
     */
    VERIFICANDO,

    /**
     * Indica que a tarefa foi concluída.
     */
    CONCLUIDA
}
