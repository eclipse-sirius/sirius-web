/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';

import { Example } from './Example';
import { BorderBox } from './model/BorderBox';
import { BorderNode } from './model/BorderNode';
import { BorderNodesBox } from './model/BorderNodesBox';
import { ContentBox } from './model/ContentBox';
import { InsideLabelBox } from './model/InsideLabelBox';
import { MarginBox } from './model/MarginBox';
import { OutsideLabelBox } from './model/OutsideLabelBox';
import { ClassAttribute } from './sample/ClassAttribute';
import { ClassMethod } from './sample/ClassMethod';
import { ImageNodeWithBottomOutsideLabel } from './sample/ImageNodeWithBottomOutsideLabel';
import { ImageNodeWithLeftOutsideLabel } from './sample/ImageNodeWithLeftOutsideLabel';
import { ImageNodeWithRightOutsideLabel } from './sample/ImageNodeWithRightOutsideLabel';

export const Main = () => {
  return (
    <Container maxWidth="lg">
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          justifyItems: 'flex-start',
          alignContent: 'stretch',
          gap: '50px',
          padding: '20px',
        }}>
        <Typography variant="h3">Layout</Typography>

        <Example label="Node with inside label">
          <Typography gutterBottom>
            Here we have an example showing a node with a size of 150px by 70px. This node does not have anything in its
            content box visible in blue. It has an inside label in the green inside label box and a small border which
            is visible thanks to the border box with a width of 1px. On top of that, there is a margin of 25px to
            prevent other nodes from being stuck near this one. The size of the node is in reality the size of the
            border box.
          </Typography>

          <MarginBox width={25} withBorder>
            <OutsideLabelBox>
              <BorderNodesBox>
                <BorderBox width="150px" height="70px" borderWidth={1}>
                  <InsideLabelBox label="Inside label" position="TOP_CENTER">
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>

        <Example label="Node size">
          <Typography gutterBottom>The following nodes all have the same size.</Typography>

          <MarginBox width={25} withBorder>
            <OutsideLabelBox>
              <BorderNodesBox>
                <BorderBox width="150px" height="70px" borderWidth={1}>
                  <InsideLabelBox label="Inside label" position="TOP_CENTER">
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>

          <MarginBox width={25} withBorder>
            <OutsideLabelBox>
              <BorderNodesBox>
                <BorderBox width="150px" height="70px" borderWidth={15}>
                  <InsideLabelBox label="Inside label" position="TOP_CENTER">
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>

          <MarginBox width={25} withBorder>
            <OutsideLabelBox>
              <BorderNodesBox>
                <BorderBox width="150px" height="70px" borderWidth={20}>
                  <InsideLabelBox label="Inside label" position="BOTTOM_CENTER">
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>

          <MarginBox width={25} withBorder>
            <OutsideLabelBox label="Some large label outside of my element" position="BOTTOM_CENTER">
              <BorderNodesBox>
                <BorderBox width="150px" height="70px" borderWidth={5}>
                  <InsideLabelBox>
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>

        <Example label="Node with outside label">
          <Typography gutterBottom>
            In this other example, instead of having an inside label, we have an outside label which can significantly
            change the footprint of the node but not its size. Here we still have a size of 150px by 70px event if we
            have a bigger border of 10x.
          </Typography>
          <MarginBox width={25} withBorder>
            <OutsideLabelBox label="Some label outside of my element" position="BOTTOM_CENTER">
              <BorderNodesBox>
                <BorderBox width="150px" height="70px" borderWidth={10}>
                  <InsideLabelBox>
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>

        <Example label="Fixed size node with right outside label">
          <Typography gutterBottom>
            This new example shows a smaller node with an image as the background of the content. The content has an
            hardcoded size of 26px by 26px which includes a border of 1px. On top of that, it has an outside label
            located in the left of the node. This node does not have any margin.
          </Typography>
          <ImageNodeWithRightOutsideLabel />
        </Example>

        <Example label="Node with a border node">
          <Typography gutterBottom>
            Now we can reuse the previous node in order to use border nodes. Here we have a very basic node with a size
            of 150px by 70px with a 10px border. We have added a border node to its right and we can thus see the impact
            on its footprint with the purple border nodes box.
          </Typography>
          <MarginBox width={25} withBorder>
            <OutsideLabelBox>
              <BorderNodesBox borderNodes={<BorderNode side="RIGHT" component={ImageNodeWithRightOutsideLabel} />}>
                <BorderBox width="150px" height="70px" borderWidth={10}>
                  <InsideLabelBox>
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>

        <Example label="Node with multiple border nodes">
          <Typography gutterBottom>We can of course provide border nodes both on the left and on the righ.</Typography>
          <MarginBox width={25} withBorder>
            <OutsideLabelBox>
              <BorderNodesBox
                borderNodes={[
                  <BorderNode side="LEFT" component={ImageNodeWithLeftOutsideLabel} />,
                  <BorderNode side="RIGHT" component={ImageNodeWithRightOutsideLabel} />,
                ]}>
                <BorderBox width="150px" height="70px" borderWidth={1}>
                  <InsideLabelBox label="Inside label" position="BOTTOM_CENTER">
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>

        <Example label="Node with a border node and an outside label">
          <Typography gutterBottom>
            Here you can see why the border nodes box has a symetric impact on the footprint of the node. It allows us
            to position the outside label of the node easily since we do not have to deeply analyze the internal
            structure of the node. We only have to position the outside label in its box, nothing more.
          </Typography>
          <MarginBox width={25} withBorder>
            <OutsideLabelBox
              label="Some label outside of my element Some label outside of my element Some label outside of my element"
              position="BOTTOM_CENTER">
              <BorderNodesBox borderNodes={[<BorderNode side="BOTTOM" component={ImageNodeWithBottomOutsideLabel} />]}>
                <BorderBox width="150px" height="70px" borderWidth={10}>
                  <InsideLabelBox>
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>

        <Example label="Node with a border node below and a right outside label">
          <Typography gutterBottom>
            In a similar fashion, we have here an example involving a label on the right of a node which contain a
            border node on its bottom.
          </Typography>
          <MarginBox width={25} withBorder>
            <OutsideLabelBox label="Some label outside of my element" position="TOP_CENTER">
              <BorderNodesBox borderNodes={[<BorderNode side="BOTTOM" component={ImageNodeWithBottomOutsideLabel} />]}>
                <BorderBox width="150px" height="70px" borderWidth={10}>
                  <InsideLabelBox>
                    <ContentBox></ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>

        <Example label="Node with a list child layout strategy">
          <Typography gutterBottom>
            In this new example, we are showing some capabilities of the child layout strategy of a node. Here we will
            use a list child layout strategy.
          </Typography>
          <MarginBox width={25} withBorder>
            <OutsideLabelBox>
              <BorderNodesBox>
                <BorderBox width="auto" height="auto" borderWidth={1}>
                  <InsideLabelBox label="Class" position="TOP_CENTER">
                    <ContentBox childLayoutStrategy="LIST">
                      <MarginBox withBorder>
                        <OutsideLabelBox>
                          <BorderNodesBox>
                            <BorderBox width="150px" height="70px" borderWidth={1}>
                              <InsideLabelBox label="Inside label" position="TOP_CENTER">
                                <ContentBox></ContentBox>
                              </InsideLabelBox>
                            </BorderBox>
                          </BorderNodesBox>
                        </OutsideLabelBox>
                      </MarginBox>

                      <MarginBox withBorder>
                        <OutsideLabelBox>
                          <BorderNodesBox>
                            <BorderBox width="150px" height="70px" borderWidth={1}>
                              <InsideLabelBox label="Inside label" position="TOP_CENTER">
                                <ContentBox></ContentBox>
                              </InsideLabelBox>
                            </BorderBox>
                          </BorderNodesBox>
                        </OutsideLabelBox>
                      </MarginBox>
                    </ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>

        <Example label="Node with a list layout strategy and multiple children">
          <Typography gutterBottom>
            In the following example, we can see that we want to have a vertical list with multiple child nodes of
            various length. In this case, we want to see the inside label box of the child node be stretched to match
            the width of the parent node.
          </Typography>
          <MarginBox width={25} withBorder>
            <OutsideLabelBox>
              <BorderNodesBox>
                <BorderBox width="auto" height="auto" borderWidth={1}>
                  <InsideLabelBox label="Class" position="TOP_CENTER">
                    <ContentBox childLayoutStrategy="LIST" justifyItems="stretch">
                      <ClassAttribute />
                      <ClassAttribute />
                      <ClassAttribute />
                      <ClassAttribute />
                      <ClassAttribute />
                      <ClassMethod />
                      <ClassMethod />
                      <ClassMethod />
                    </ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>

        <Example label="Node with a list layout strategy and multiple children">
          <Typography gutterBottom>
            In this other example, we also have various child nodes of various sizes and footprints but this time, we
            want to see the content box of the child nodes be expanded.
          </Typography>
          <MarginBox width={25} withBorder>
            <OutsideLabelBox>
              <BorderNodesBox>
                <BorderBox width="auto" height="auto" borderWidth={1}>
                  <InsideLabelBox label="Class" position="TOP_CENTER">
                    <ContentBox childLayoutStrategy="LIST" justifyItems="stretch">
                      <MarginBox withBorder>
                        <OutsideLabelBox alignChildren="stretch">
                          <BorderNodesBox stretchChildren>
                            <BorderBox minWidth="150px" height="200px" borderWidth={1}>
                              <InsideLabelBox label="150px x 200px content width stretched" position="TOP_CENTER">
                                <ContentBox></ContentBox>
                              </InsideLabelBox>
                            </BorderBox>
                          </BorderNodesBox>
                        </OutsideLabelBox>
                      </MarginBox>

                      <MarginBox withBorder>
                        <OutsideLabelBox>
                          <BorderNodesBox>
                            <BorderBox width="300px" height="200px" borderWidth={1}>
                              <InsideLabelBox label="300px x 200px" position="TOP_CENTER">
                                <ContentBox></ContentBox>
                              </InsideLabelBox>
                            </BorderBox>
                          </BorderNodesBox>
                        </OutsideLabelBox>
                      </MarginBox>
                    </ContentBox>
                  </InsideLabelBox>
                </BorderBox>
              </BorderNodesBox>
            </OutsideLabelBox>
          </MarginBox>
        </Example>
      </div>
    </Container>
  );
};
