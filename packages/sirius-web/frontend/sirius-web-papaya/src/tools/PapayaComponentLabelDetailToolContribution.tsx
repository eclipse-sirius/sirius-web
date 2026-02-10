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
import { PaletteQuickToolContributionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import Slideshow from '@mui/icons-material/Slideshow';
import { ListItem, ListItemText } from '@mui/material';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import { Theme } from '@mui/material/styles';
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { Fragment, useState } from 'react';
import { makeStyles } from 'tss-react/mui';

const useToolStyle = makeStyles()((theme: Theme) => ({
  tool: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

type Modal = 'dialog';
export const PapayaComponentLabelDetailToolContribution = ({
  representationElementIds,
}: PaletteQuickToolContributionComponentProps) => {
  const [modal, setModal] = useState<Modal | null>(null);
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { classes } = useToolStyle();

  const targetedNodes = representationElementIds
    .map((elementId) => store.getState().nodeLookup.get(elementId))
    .filter((element) => !!element);

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
              <ListItem disablePadding key={node?.id}>
                <ListItemText primary={node?.data.insideLabel?.text} />
              </ListItem>
            ))}
          </List>
        </Dialog>
      </>
    );
  }

  return (
    <Fragment key="label-detail-modal-contribution">
      <IconButton
        className={classes.tool}
        size="small"
        color="inherit"
        aria-label="Label detail"
        title="Label detail"
        onClick={() => setModal('dialog')}
        data-testid="label-detail">
        <Slideshow sx={{ fontSize: 16 }} />
      </IconButton>
      {modalElement}
    </Fragment>
  );
};
