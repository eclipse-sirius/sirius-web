/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { IconButton } from 'core/button/Button';
import { Select } from 'core/select/Select';
import { ArrangeAll, FitToScreen, Share, ZoomIn, ZoomOut } from 'icons';
import { ShareDiagramModal } from 'modals/share-diagram/ShareDiagramModal';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import styles from './Toolbar.module.css';

const zooms = [
  { id: '4', label: '400%' },
  { id: '2', label: '200%' },
  { id: '1.75', label: '175%' },
  { id: '1.5', label: '150%' },
  { id: '1.25', label: '125%' },
  { id: '1', label: '100%' },
  { id: '0.75', label: '75%' },
  { id: '0.5', label: '50%' },
  { id: '0.25', label: '25%' },
  { id: '0.1', label: '10%' },
  { id: '0.05', label: '5%' },
];

const propTypes = {
  onZoomIn: PropTypes.func.isRequired,
  onZoomOut: PropTypes.func.isRequired,
  onFitToScreen: PropTypes.func.isRequired,
  onArrangeAll: PropTypes.func.isRequired,
  setZoomLevel: PropTypes.func.isRequired,
  zoomLevel: PropTypes.string,
};
export const Toolbar = ({ onZoomIn, onZoomOut, onFitToScreen, onArrangeAll, setZoomLevel, zoomLevel }) => {
  const [state, setState] = useState({ modal: undefined, currentZoomLevel: undefined });
  const onShare = () => setState({ modal: 'ShareDiagramModal', currentZoomLevel: state.currentZoomLevel });
  const closeModal = () => setState({ modal: undefined, currentZoomLevel: state.currentZoomLevel });

  const { modal, currentZoomLevel } = state;

  useEffect(() => {
    setState({ modal: undefined, currentZoomLevel: zoomLevel });
  }, [zoomLevel]);

  const updateZoomLevel = (event) => {
    const newZoomLevel = event.target.value;
    setState({ modal: state.modal, currentZoomLevel: newZoomLevel.toString() });
    setZoomLevel(newZoomLevel.toString());
  };

  let modalElement = null;
  if (modal === 'ShareDiagramModal') {
    modalElement = <ShareDiagramModal url={window.location.href} onClose={closeModal} />;
  }
  return (
    <>
      <div className={styles.toolbar}>
        <IconButton className={styles.icon} onClick={onShare} data-testid="share">
          <Share title="Share" />
        </IconButton>
        <div className={styles.separator} />
        <IconButton className={styles.icon} onClick={onZoomIn} data-testid="zoom-in">
          <ZoomIn />
        </IconButton>
        <IconButton className={styles.icon} onClick={onZoomOut} data-testid="zoom-out">
          <ZoomOut />
        </IconButton>
        <IconButton className={styles.icon} onClick={onFitToScreen} data-testid="fit-to-screen">
          <FitToScreen title="Fit to screen" />
        </IconButton>
        <IconButton className={styles.icon} onClick={onArrangeAll} data-testid="arrangeAll">
          <ArrangeAll title="Arrange All" />
        </IconButton>
        <Select
          name="zoom level"
          value={currentZoomLevel}
          options={zooms}
          onChange={updateZoomLevel}
          small={true}
          data-testid="zoom-level"
        />
      </div>
      {modalElement}
    </>
  );
};
Toolbar.propTypes = propTypes;
