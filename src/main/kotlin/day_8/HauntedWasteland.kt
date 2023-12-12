package day_8

import java.io.File

class HauntedWasteland(filepath: String) {
    private val lines = File(filepath).readLines()
    private val nodesRegex = "[0-Z][0-Z][0-Z]".toRegex()
    private val endNode = "ZZZ"

    fun steps(): Int = nodes().size - 1

    fun nodes(travelledNodes: List<Node> = listOf("AAA".toNode())): List<String> {
        val commands = lines.first().toCharArray()
        val newTravelledNodes = commands.fold(travelledNodes) { currentTravelledNodes, command ->
            val lastNode = currentTravelledNodes.last()
            val nextNode = if (command == 'L') lastNode.network.first
            else lastNode.network.second

            if (nextNode == endNode)
                return@fold currentTravelledNodes + nextNode.toNode()

            currentTravelledNodes + nextNode.toNode()
        }

        if (newTravelledNodes.last().name != endNode) {
            return nodes(newTravelledNodes)
        }

        return newTravelledNodes.map { it.name }
    }

    fun minimumStepsGhosts(): Long {
        val steps = allEndIndices().map { it.first() + 1}
        return steps.fold(steps.first().toLong()) { currentLCM, endNodeIndex ->
            lcm(currentLCM, endNodeIndex.toLong())
        }
    }

    private fun allEndIndices(): List<List<Int>> =
        allPaths().map {  nodes ->
            nodes.filter{ it.isEndNode() }.map { nodes.indexOf(it) }
        }

    private fun allPaths(): List<List<String>> {
        val startingNodes = lines.drop(2)
            .filter { it.isStartNode() }
            .map { it.take(3) }
            .map { it.toNode() }

        return startingNodes.map {
            fullPath(it)
        }
    }

    private fun fullPath(startingNode: Node = "AAA".toNode(), fullTravels: List<List<Node>> = emptyList()): List<String> {
        val commands = lines.first().toCharArray()

        val newTravelledNodes = commands.fold(listOf(startingNode)) { currentTravelledNodes, command ->
            val lastNode = currentTravelledNodes.last()
            val nextNode = if (command == 'L') lastNode.network.first else lastNode.network.second

            currentTravelledNodes + nextNode.toNode()
        }

        if (fullTravels.isEmpty()) fullPath(newTravelledNodes.last(), listOf(newTravelledNodes))

        return if (fullTravels.isNotEmpty() && fullTravels.contains(newTravelledNodes.drop(1)))
            (fullTravels).flatMap { it.map { node -> node.name } }
            else fullPath(newTravelledNodes.last(), fullTravels + listOf(newTravelledNodes.drop(1)))
    }

    private fun lcm(n1:Long, n2:Long): Long {
        var gcd = 1L

        var i = 1L
        while (i <= n1 && i <= n2) {
            if (n1 % i == 0L && n2 % i == 0L)
                gcd = i
            ++i
        }

        return n1 * n2 / gcd
    }

    private fun String.isEndNode() = this.toCharArray()[2] == 'Z'
    private fun String.isStartNode() = this.toCharArray()[2] == 'A'

    private fun String.toNode(): Node {
        val lineWithStartNode = lines.first { it.contains("$this = ") }
        val network = nodesRegex.findAll(lineWithStartNode).map { it.value }.toList()
        val leftNode = network.elementAt(1)
        val rightNode = network.elementAt(2)

        return Node(this, leftNode to rightNode)
    }


    data class Node(val name: String, val network: Pair<String, String>)
}