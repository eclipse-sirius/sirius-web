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
import { DialogTitle } from '@material-ui/core';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import IconButton from '@material-ui/core/IconButton';
import CodeOutlinedIcon from '@material-ui/icons/CodeOutlined';
import { Fragment, useEffect, useState } from 'react';
import { useNodes } from 'reactflow';
import { moduleMap } from './moduleMap';

type Modal = 'dialog';

function findNodeById(nodes, id) {
  return nodes.find((node) => node.id === id) || null;
}

function getGitHubSourceURL(labels) {
  const module = labels[0];
  const modulePath = moduleMap[module];
  return `https://raw.githubusercontent.com/eclipse-sirius/sirius-web/master/packages/${modulePath}/backend/${module}/src/main/java/${labels[1].replaceAll(
    /\./g,
    '/'
  )}/${labels[2]}.java`;
}

export const CodeLookupToolContribution = ({ diagramElementId }: DiagramPaletteToolContributionComponentProps) => {
  const [modal, setModal] = useState<Modal | null>(null);

  const nodes = useNodes<NodeData>();
  const targetedNode = findNodeById(nodes, diagramElementId);

  const [sourceURL, setSourceURL] = useState<string | null>(null);
  useEffect(() => {
    if (modal) {
      const path = [targetedNode];
      while (path.at(-1)?.parentNode) {
        path.push(findNodeById(nodes, path.at(-1).parentNode));
      }
      path.reverse();
      const labels = path.map((node) => node.data.label.text);
      setSourceURL(getGitHubSourceURL(labels));
    }
  }, [diagramElementId, modal]);

  const [source, setSource] = useState<string | null>(null);
  useEffect(() => {
    if (sourceURL) {
      const fetchSource = async () => {
        const response = await fetch(sourceURL, {
          headers: {
            'Content-Type': 'text/plain',
          },
        });
        if (response.ok) {
          setSource(await response.text());
        }
      };
      fetchSource();
    }
  }, [sourceURL]);

  const onClose = () => {
    setModal(null);
  };

  let modalElement: JSX.Element | null = null;
  if (modal === 'dialog' && targetedNode && sourceURL) {
    const link = sourceURL.replace('raw.githubusercontent', 'github').replace('master', 'blob/master');
    modalElement = (
      <Dialog open={sourceURL !== null} onClose={onClose} fullWidth maxWidth="lg">
        <DialogTitle id="simple-dialog-title">
          <a href={link} target="_blank">
            Show on GitHub
          </a>
        </DialogTitle>
        <DialogContent>
          <DialogContentText>{source ? <pre>{source}</pre> : 'Loading from GitHub'}</DialogContentText>
        </DialogContent>
      </Dialog>
    );
  }

  return (
    <Fragment key="code-lookup-modal-contribution">
      <IconButton
        size="small"
        color="inherit"
        aria-label="Code Lookup"
        title="Code Lookup"
        onClick={() => setModal('dialog')}
        data-testid="code-lookup">
        <CodeOutlinedIcon />
      </IconButton>
      {modalElement}
    </Fragment>
  );
};
