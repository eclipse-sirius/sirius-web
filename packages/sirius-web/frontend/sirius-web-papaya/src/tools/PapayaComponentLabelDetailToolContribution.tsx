/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { NodeData } from '@eclipse-sirius/sirius-components-diagrams';
import { PaletteToolContributionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import Slideshow from '@mui/icons-material/Slideshow';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import IconButton from '@mui/material/IconButton';
import { Theme } from '@mui/material/styles';
import { Node, useNodes } from '@xyflow/react';
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
  representationElementId,
}: PaletteToolContributionComponentProps) => {
  const [modal, setModal] = useState<Modal | null>(null);
  const { classes } = useToolStyle();
  const nodes = useNodes<Node<NodeData>>();
  const targetedNode = nodes.find((node) => node.id === representationElementId);
  if (
    !targetedNode ||
    targetedNode.data.targetObjectKind !== 'siriusComponents://semantic?domain=papaya&entity=Component'
  ) {
    return null;
  }

  const onClose = () => {
    setModal(null);
  };

  let modalElement: JSX.Element | null = null;
  if (modal === 'dialog' && targetedNode) {
    modalElement = (
      <>
        <Dialog open={true} onClose={onClose} fullWidth>
          <DialogContent>
            <DialogContentText>{targetedNode.data.insideLabel?.text}</DialogContentText>
          </DialogContent>
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
