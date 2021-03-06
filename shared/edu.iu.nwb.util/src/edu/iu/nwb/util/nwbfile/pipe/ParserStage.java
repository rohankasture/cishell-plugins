package edu.iu.nwb.util.nwbfile.pipe;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Objects;

import edu.iu.nwb.util.nwbfile.NWBFileParserHandler;

/**
 * ParserStages are "pipe segments" in the pipe of NWBFileParserHandlers that process a file.
 * They form a linked list, each segment points to the next one with its {@code delegate}
 * field.  Normally, you won't need to interact with them directly unless you're writing
 * something else that goes in this package.  You'd use {@link ParserPipe} to make a
 * pipeline of them, without ever having to deal with them directly.
 * <p>
 * TODO: extract an interface, add an "isExtendable" method, change this to ParserStageAdapter?
 *
 * @author thgsmith
 *
 */
public class ParserStage implements NWBFileParserHandler {
	protected NWBFileParserHandler delegate;
	private boolean parsingStarted = false;

	public ParserStage(NWBFileParserHandler delegate) {
		setNextStage(delegate);
	}
	
	public ParserStage() {
		setNextStage(GuardParserHandler.getInstance());
	}

	void setNextStage(NWBFileParserHandler next) {
		if (parsingStarted) {
			throw new IllegalStateException("Pipeline setup changed after parsing started");
		}
		this.delegate = next;
	}
	
	@Override
	public void setNodeCount(int numberOfNodes) {
		parsingStarted = true;
		delegate.setNodeCount(numberOfNodes);
	}

	@Override
	public void setNodeSchema(LinkedHashMap<String, String> schema) {
		parsingStarted = true;
		delegate.setNodeSchema(schema);
	}

	@Override
	public void addNode(int id, String label, Map<String, Object> attributes) {
		parsingStarted = true;
		delegate.addNode(id, label, attributes);
	}

	@Override
	public void setDirectedEdgeCount(int numberOfEdges) {
		parsingStarted = true;
		delegate.setDirectedEdgeCount(numberOfEdges);
	}

	@Override
	public void setDirectedEdgeSchema(LinkedHashMap<String, String> schema) {
		parsingStarted = true;
		delegate.setDirectedEdgeSchema(schema);
	}

	@Override
	public void addDirectedEdge(int sourceNode, int targetNode, Map<String, Object> attributes) {
		parsingStarted = true;
		delegate.addDirectedEdge(sourceNode, targetNode, attributes);
	}

	@Override
	public void setUndirectedEdgeCount(int numberOfEdges) {
		parsingStarted = true;
		delegate.setUndirectedEdgeCount(numberOfEdges);
	}

	@Override
	public void setUndirectedEdgeSchema(LinkedHashMap<String, String> schema) {
		parsingStarted = true;
		delegate.setUndirectedEdgeSchema(schema);
	}

	@Override
	public void addUndirectedEdge(int node1, int node2, Map<String, Object> attributes) {
		parsingStarted = true;
		delegate.addUndirectedEdge(node1, node2, attributes);
	}

	@Override
	public void addComment(String comment) {
		parsingStarted = true;
		delegate.addComment(comment);
	}

	@Override
	public void finishedParsing() {
		parsingStarted = true;
		delegate.finishedParsing();
	}

	@Override
	public boolean haltParsingNow() {
		parsingStarted = true;
		return delegate.haltParsingNow();
	}


	public String nextToString() {
		if (this.delegate == null) {
			return "none";
		} else {
			return delegate.toString();
		}
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("next", nextToString())
				.toString();
	}

	public boolean hasValidDelegate() {
		return this.delegate != null && this.delegate != GuardParserHandler.getInstance();
	}

}