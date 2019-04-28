package se.bjurr.pomdependencyanalyzer.work.graph;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.io.Files;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.util.mxCellRenderer;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.io.DOTImporter;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.VertexProvider;
import org.jgrapht.traverse.DepthFirstIterator;
import se.bjurr.pomdependencyanalyzer.data.Dependency;

public class DependencyGraph {

  private final File dotFile;
  private boolean createImage;

  public DependencyGraph(final File dotFile, final boolean createImage) {
    this.dotFile = dotFile;
  }

  public void createImage(final Graph<DependencyVertex, DependencyEdge> graph) throws IOException {
    final File analyzedFile =
        dotFile.getParentFile().toPath().resolve(dotFile.getName() + ".analyzed.png").toFile();
    System.out.println("Writing image of analyzed file to: " + analyzedFile.getPath());
    final JGraphXAdapter<DependencyVertex, DependencyEdge> graphAdapter =
        new JGraphXAdapter<DependencyVertex, DependencyEdge>(graph);
    final mxCompactTreeLayout layout = new mxCompactTreeLayout(graphAdapter);
    layout.setHorizontal(false);
    layout.setEdgeRouting(true);
    layout.setLevelDistance(50);
    layout.setNodeDistance(20);
    layout.setMoveTree(true);
    layout.execute(graphAdapter.getDefaultParent());
    final BufferedImage image =
        mxCellRenderer.createBufferedImage(graphAdapter, null, 1, Color.WHITE, true, null);
    ImageIO.write(image, "PNG", analyzedFile);
  }

  public ResolvedDependencies getResolvedDependencies() throws IOException, Throwable {
    final VertexProvider<DependencyVertex> vertexProvider =
        (id, attributes) -> {
          final DependencyParser dp = new DependencyParser(id);
          return new DependencyVertex(dp.getDependency());
        };
    final List<DependencyVertex> toRemove = new ArrayList<>();
    final EdgeProvider<DependencyVertex, DependencyEdge> edgeProvider =
        (from, to, label, attributes) -> {
          if (from.getDependency().getScope().equals("provided")
              && to.getDependency().getScope().equals("provided")) {
            toRemove.add(to);
          }
          final String scope = to.getDependency().getScope();
          return new DependencyEdge(scope);
        };

    final DOTImporter<DependencyVertex, DependencyEdge> importer =
        new DOTImporter<DependencyVertex, DependencyEdge>(vertexProvider, edgeProvider);
    final String dotContent = Files.toString(dotFile, UTF_8);

    try {
      final Graph<DependencyVertex, DependencyEdge> graph =
          new DirectedMultigraph<>(DependencyEdge.class);
      importer.importGraph(graph, new StringReader(dotContent));
      graph.removeAllVertices(toRemove);
      System.out.println("Vertexes: " + graph.vertexSet().size());
      System.out.println("Edges: " + graph.edgeSet().size());
      if (createImage) {
        createImage(graph);
      }
      return toSet(graph);
    } catch (final Throwable t) {
      System.out.println("Could not parse:\n\n" + dotContent + "\n\n");
      throw t;
    }
  }

  private ResolvedDependencies toSet(final Graph<DependencyVertex, DependencyEdge> graph) {
    final Set<Dependency> list = new TreeSet<>();
    final Iterator<DependencyVertex> iterator = new DepthFirstIterator<>(graph);
    Dependency parsed = null;
    if (iterator.hasNext()) {
      parsed = iterator.next().getDependency();
    }
    while (iterator.hasNext()) {
      list.add(iterator.next().getDependency());
    }
    return new ResolvedDependencies(parsed, list);
  }
}
