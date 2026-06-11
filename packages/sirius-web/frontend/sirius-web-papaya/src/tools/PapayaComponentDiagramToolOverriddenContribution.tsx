/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { EdgeData, NodeData } from '@eclipse-sirius/sirius-components-diagrams';
import { PaletteQuickToolContributionComponentProps } from '@eclipse-sirius/sirius-components-palette/dist/extensions/PaletteQuickToolContribution.types';
import Slideshow from '@mui/icons-material/Slideshow';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { Edge, InternalNode, Node, useStoreApi } from '@xyflow/react';
import { Fragment, useState } from 'react';
import { makeStyles } from 'tss-react/mui';

const useStyle = makeStyles()(() => ({
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
  listItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
}));

type Modal = 'dialog';

export const PapayaComponentDiagramToolOverriddenContribution = ({
  representationElementIds,
}: PaletteQuickToolContributionComponentProps) => {
  const [modal, setModal] = useState<Modal | null>(null);
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { classes } = useStyle();

  const shouldRender = representationElementIds.every((elementId) =>
    store
      .getState()
      .nodeLookup.get(elementId)
      ?.data.targetObjectKind.startsWith('siriusComponents://semantic?domain=papaya&entity=Component')
  );

  const targetedNodes: InternalNode<Node<NodeData>>[] = representationElementIds
    .map((elementId) => store.getState().nodeLookup.get(elementId))
    .filter((element): element is InternalNode<Node<NodeData>> => !!element);

  const onClose = () => {
    setModal(null);
  };

  let modalElement: JSX.Element | null = null;
  if (modal === 'dialog') {
    modalElement = (
      <>
        <Dialog open={true} onClose={onClose} fullWidth>
          <DialogTitle>Inside labels :</DialogTitle>
          <List>
            {targetedNodes.map((node) => (
              <ListItem disablePadding key={node.id}>
                <ListItemText primary={node.data.insideLabel?.text} />
              </ListItem>
            ))}
          </List>
        </Dialog>
      </>
    );
  }

  if (!shouldRender) {
    return null;
  }

  return (
    <Fragment key="label-detail-modal-contribution">
      <Tooltip key={'tooltip_'} title={'details'} placement="right">
        <ListItemButton
          onClick={() => setModal('dialog')}
          data-testid="overridden_tool_detail"
          className={classes.listItemButton}>
          <Slideshow sx={{ fontSize: 16, marginRight: 2 }} />
          <ListItemText primary={'Details'} className={classes.listItemText} />
        </ListItemButton>
      </Tooltip>
      {modalElement}
    </Fragment>
  );
};
