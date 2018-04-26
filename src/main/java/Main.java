import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.GroupType;
import org.biojava.nbio.structure.io.PDBFileReader;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String filename = "C:\\Users\\artem\\Desktop\\carbon\\pdb5pti.ent.gz";
        PDBFileReader pdbReader = new PDBFileReader();
        try {
            pdbReader.getStructure(filename).getChains(0)
                    .stream().flatMap(i -> i.getAtomGroups(GroupType.AMINOACID).stream())
                    .map(group -> {
                        Graph graph = new SingleGraph(group.getPDBName());
                        System.out.println("pdb_name: " + group.getPDBName());
                        List<Atom> atoms = group.getAtoms();
                        atoms.forEach(atom -> {
                            Node node = graph.addNode(atom.getName());
                            node.setAttribute("xyz", atom.getCoords());
                            node.addAttribute("ui.label", atom.getName());
                            System.out.println(String.format("atom_name: %s,X: %f, Y: %f, Z: %f", atom.getName(), atom.getX(), atom.getY(), atom.getZ()));
                        });
                        return graph.display(true);
                    }).forEach(viewer -> {
                viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
                viewer.disableAutoLayout();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}