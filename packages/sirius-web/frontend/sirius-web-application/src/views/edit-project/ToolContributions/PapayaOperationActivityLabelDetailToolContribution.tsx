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
import {
  DiagramPaletteToolContributionComponentProps,
  NodeData,
} from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import IconButton from '@material-ui/core/IconButton';
import { Slideshow } from '@material-ui/icons';
import { Fragment, useState } from 'react';
import { useNodes } from 'reactflow';

type Modal = 'dialog';
export const PapayaOperationActivityLabelDetailToolContribution = ({
  diagramElementId,
}: DiagramPaletteToolContributionComponentProps) => {
  const [modal, setModal] = useState<Modal | null>(null);

  const nodes = useNodes<NodeData>();
  const targetedNode = nodes.find((node) => node.id === diagramElementId);

  const onClose = () => {
    setModal(null);
  };

  let modalElement: JSX.Element | null = null;
  if (modal === 'dialog' && targetedNode) {
    modalElement = (
      <>
        <Dialog open={true} onClose={onClose} fullWidth>
          <DialogContent>
            <DialogContentText>{targetedNode.data.insideLabel.text}</DialogContentText>
          </DialogContent>
        </Dialog>
      </>
    );
  }

  return (
    <Fragment key="label-detail-modal-contribution">
      <IconButton
        size="small"
        color="inherit"
        aria-label="Label detail"
        title="Label detail"
        onClick={() => setModal('dialog')}
        data-testid="label-detail">
        <Slideshow />
      </IconButton>
      {modalElement}
    </Fragment>
  );
};
