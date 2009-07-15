package edu.iu.nwb.analysis.pagerank.weighted;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.cishell.framework.algorithm.AlgorithmExecutionException;

import edu.iu.nwb.util.nwbfile.NWBFileParserAdapter;

public class OnDiskGraph extends Graph {

	private File nwbFile;
	private WeightAccessor weightAccessor;

	public OnDiskGraph(File nwbFile, WeightAccessor weightAccessor, int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
		this.nwbFile = nwbFile;
		this.weightAccessor = weightAccessor;
	}

	@Override
	public void performEdgePass(final EdgeHandler handler) throws AlgorithmExecutionException {
		WeightedPagerank.parseNWBFile(nwbFile, new NWBFileParserAdapter() {
			public void addDirectedEdge(int source, int target, Map attributes) {
				handler.handleEdge(new Edge(source, target, weightAccessor.getWeight(attributes)));
			}
		});
		
	}

}
