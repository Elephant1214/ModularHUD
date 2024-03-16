package me.elephant1214.modularhud.api.component.condition

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.component.context.ComponentHandler

@Serializable
class ConditionComposite(
    private val op: ConditionOp,
    private val operands: List<Condition>
) : Condition() {
    constructor(conditions: List<Condition>) : this(ConditionOp.AND, conditions)

    override fun compute(handler: ComponentHandler): Boolean = when (this.op) {
        ConditionOp.AND -> this.operands.all { it.compute(handler) }
        ConditionOp.OR -> this.operands.any { it.compute(handler) }
    }
}
