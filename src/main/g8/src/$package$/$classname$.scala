package $package$

import com.jme3.app.SimpleApplication
import com.jme3.math.{ColorRGBA, Vector3f}
import com.jme3.scene.Geometry
import projectkyoto.jme3.mmd._
import projectkyoto.mmd.file.VMDFile
import projectkyoto.jme3.mmd.vmd.VMDControl
import com.jme3.light.{PointLight, AmbientLight, DirectionalLight}
import com.jme3.texture.plugins.AndroidImageLoader

/**
 * Created with IntelliJ IDEA.
 * User: kobayasi
 * Date: 13/09/03
 * Time: 0:29
 * To change this template use File | Settings | File Templates.
 */
class $classname$ extends SimpleApplication {
  def simpleInitApp {
    assetManager.registerLoader(classOf[PMDLoaderGLSLSkinning2], "pmd")
    assetManager.registerLoader(classOf[VMDLoader], "vmd")
    assetManager.registerLoader(classOf[AndroidImageLoader],"sph","spa")

    flyCam.setMoveSpeed(50)
    flyCam.setDragToRotate(true)
    val pmdNode: PMDNode = assetManager.loadModel("/Model/sora/sora_act2.5.pmd").asInstanceOf[PMDNode]
    val vmd: VMDFile = assetManager.loadAsset("/motion/koshihuri.vmd").asInstanceOf[VMDFile]
    vmdControl = new VMDControl(pmdNode, vmd)
    pmdNode.addControl(vmdControl)
    pmdNode.addControl(new UpdateControl(pmdNode))
    vmdControl.setFrameNo(0)
    vmdControl.setPause(true)
    rootNode.attachChild(pmdNode)
    val dl: DirectionalLight = new DirectionalLight
    dl.setDirection(new Vector3f(1, 0, -5).normalizeLocal)
    dl.setColor(ColorRGBA.White.mult(0.5f))
    rootNode.addLight(dl)
    val al: AmbientLight = new AmbientLight
    al.setColor(ColorRGBA.White.mult(1.0f))
    rootNode.addLight(al)
    val cartoonEdgeProcess: CartoonEdgeProcessor = new CartoonEdgeProcessor
    viewPort.addProcessor(cartoonEdgeProcess)
    cam.setLocation(new Vector3f(0, 10, 40))
  }

  override def simpleUpdate(tpf: Float) {
    time += tpf
    if (time > 5) {
      if (vmdControl != null) {
        vmdControl.setPause(false)
      }
    }
  }

  private var pl: PointLight = null
  private var lightMdl: Geometry = null
  private var vmdControl: VMDControl = null
  private var time: Float = 0
}
