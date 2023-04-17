/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo and others.
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
import { ApolloError, gql, useMutation } from '@apollo/client';
import Avatar from '@material-ui/core/Avatar';
import FormControl from '@material-ui/core/FormControl';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
import AccountTreeIcon from '@material-ui/icons/AccountTree';
import AspectRatioIcon from '@material-ui/icons/AspectRatio';
import CloseIcon from '@material-ui/icons/Close';
import ShareIcon from '@material-ui/icons/Share';
import TonalityIcon from '@material-ui/icons/Tonality';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';
import { useMachine } from '@xstate/react';
import React, { useCallback, useEffect } from 'react';
import { GQLDiagram, GQLNode } from '../representation/DiagramRepresentation.types';
import { ShareDiagramModal } from '../share-diagram/ShareDiagramModal';
import {
  GQLErrorPayload,
  GQLFadeDiagramElementData,
  GQLFadeDiagramElementInput,
  GQLFadeDiagramElementVariables,
  GQLHideDiagramElementData,
  GQLHideDiagramElementInput,
  GQLHideDiagramElementVariables,
  ToolbarProps,
} from './Toolbar.types';
import {
  CloseModalEvent,
  HideToastEvent,
  SchemaValue,
  SetZoomLevelEvent,
  ShareDiagramModalEvent,
  ShowToastEvent,
  ToolbarContext,
  ToolbarEvent,
  toolbarMachine,
} from './ToolbarMachine';

const hideDiagramElementMutation = gql`
  mutation hideDiagramElement($input: HideDiagramElementInput!) {
    hideDiagramElement(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const fadeDiagramElementMutation = gql`
  mutation fadeDiagramElement($input: FadeDiagramElementInput!) {
    fadeDiagramElement(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const useToolbarStyles = makeStyles((theme) => ({
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    borderBottomColor: theme.palette.divider,
  },
  selectFormControl: {
    minWidth: 70,
  },
  subscribers: {
    marginLeft: 'auto',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    '& > *': {
      marginLeft: theme.spacing(0.5),
      marginRight: theme.spacing(0.5),
    },
  },
  avatar: {
    fontSize: '1rem',
    width: theme.spacing(3),
    height: theme.spacing(3),
  },
}));

export const Toolbar = ({
  editingContextId,
  representationId,
  diagram,
  onZoomIn,
  onZoomOut,
  onFitToScreen,
  onArrangeAll,
  readOnly,
  setZoomLevel,
  autoLayout,
  zoomLevel,
  subscribers,
}: ToolbarProps) => {
  const [{ value, context }, dispatch] = useMachine<ToolbarContext, ToolbarEvent>(toolbarMachine);
  const { toast } = value as SchemaValue;
  const { modal, currentZoomLevel, message } = context;

  const classes = useToolbarStyles();

  const onShare = () => {
    const shareDiagramModalEvent: ShareDiagramModalEvent = {
      type: 'SHARE_DIAGRAM_MODAL',
      modal: 'ShareDiagramModal',
    };
    dispatch(shareDiagramModalEvent);
  };

  const closeModal = () => {
    const closeModalEvent: CloseModalEvent = {
      type: 'CLOSE_MODAL',
    };
    dispatch(closeModalEvent);
  };

  useEffect(() => {
    const setZoomLevelEvent: SetZoomLevelEvent = {
      type: 'SET_ZOOM_LEVEL',
      currentZoomLevel: zoomLevel,
    };
    dispatch(setZoomLevelEvent);
  }, [zoomLevel, dispatch]);

  const updateZoomLevel = (event) => {
    const newZoomLevel = event.target.value;
    const setZoomLevelEvent: SetZoomLevelEvent = {
      type: 'SET_ZOOM_LEVEL',
      currentZoomLevel: newZoomLevel.toString(),
    };
    dispatch(setZoomLevelEvent);
    setZoomLevel(newZoomLevel.toString());
  };

  const isErrorPayload = (payload): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

  const handleError = useCallback(
    (loading: boolean, data, error: ApolloError) => {
      if (!loading) {
        if (error) {
          const showToastEvent: ShowToastEvent = {
            type: 'SHOW_TOAST',
            message: 'An error has occurred while executing this action, please contact the server administrator',
          };
          dispatch(showToastEvent);
        }
        if (data) {
          const keys = Object.keys(data);
          if (keys.length > 0) {
            const firstKey = keys[0];
            const firstField = data[firstKey];
            if (isErrorPayload(firstField)) {
              const { message } = firstField;
              const showToastEvent: ShowToastEvent = {
                type: 'SHOW_TOAST',
                message,
              };
              dispatch(showToastEvent);
            }
          }
        }
      }
    },
    [dispatch]
  );

  const getAllNodes = (diagram: GQLDiagram): GQLNode[] => {
    const getAllNodesRec = (node: GQLNode) => [
      node,
      ...[...(node.borderNodes || []), ...(node.childNodes || [])].flatMap(getAllNodesRec),
    ];
    return diagram.nodes.flatMap(getAllNodesRec);
  };

  const [
    hideElementMutation,
    { loading: hideDiagramElementLoading, data: hideDiagramElementData, error: hideDiagramElementError },
  ] = useMutation<GQLHideDiagramElementData, GQLHideDiagramElementVariables>(hideDiagramElementMutation);

  useEffect(() => {
    handleError(hideDiagramElementLoading, hideDiagramElementData, hideDiagramElementError);
  }, [hideDiagramElementLoading, hideDiagramElementData, hideDiagramElementError, handleError]);

  const onUnhideAll = () => {
    const input: GQLHideDiagramElementInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      elementIds: [...diagram.edges, ...getAllNodes(diagram)].map((elem) => elem.id),
      hide: false,
    };
    hideElementMutation({ variables: { input } });
  };

  const [
    fadeElementMutation,
    { loading: fadeDiagramElementLoading, data: fadeDiagramElementData, error: fadeDiagramElementError },
  ] = useMutation<GQLFadeDiagramElementData, GQLFadeDiagramElementVariables>(fadeDiagramElementMutation);

  useEffect(() => {
    handleError(fadeDiagramElementLoading, fadeDiagramElementData, fadeDiagramElementError);
  }, [fadeDiagramElementLoading, fadeDiagramElementData, fadeDiagramElementError, handleError]);

  const onUnfadeAll = () => {
    const input: GQLFadeDiagramElementInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      elementIds: [...diagram.edges, ...getAllNodes(diagram)].map((elem) => elem.id),
      fade: false,
    };
    fadeElementMutation({ variables: { input } });
  };

  let modalElement: React.ReactElement | null = null;
  if (modal === 'ShareDiagramModal') {
    modalElement = <ShareDiagramModal url={window.location.href} onClose={closeModal} />;
  }
  return (
    <>
      <div className={classes.toolbar}>
        <FormControl className={classes.selectFormControl}>
          <Select
            value={currentZoomLevel}
            onChange={updateZoomLevel}
            variant="standard"
            disableUnderline
            title="Zoom level"
            data-testid="zoom-level">
            <MenuItem value={'4'}>400%</MenuItem>
            <MenuItem value={'2'}>200%</MenuItem>
            <MenuItem value={'1.75'}>175%</MenuItem>
            <MenuItem value={'1.5'}>150%</MenuItem>
            <MenuItem value={'1.25'}>125%</MenuItem>
            <MenuItem value={'1'}>100%</MenuItem>
            <MenuItem value={'0.75'}>75%</MenuItem>
            <MenuItem value={'0.5'}>50%</MenuItem>
            <MenuItem value={'0.25'}>25%</MenuItem>
            <MenuItem value={'0.1'}>10%</MenuItem>
            <MenuItem value={'0.05'}>5%</MenuItem>
          </Select>
        </FormControl>
        <IconButton
          size="small"
          color="inherit"
          aria-label="zoom in"
          title="Zoom in"
          onClick={onZoomIn}
          data-testid="zoom-in">
          <ZoomInIcon fontSize="small" />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="zoom out"
          title="Zoom out"
          onClick={onZoomOut}
          data-testid="zoom-out">
          <ZoomOutIcon fontSize="small" />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="fit to screen"
          title="Fit to screen"
          onClick={onFitToScreen}
          data-testid="fit-to-screen">
          <AspectRatioIcon fontSize="small" />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="arrange-all"
          title="Arrange all"
          disabled={autoLayout || readOnly}
          onClick={onArrangeAll}
          data-testid="arrange-all">
          <AccountTreeIcon fontSize="small" />
        </IconButton>
        <IconButton size="small" color="inherit" aria-label="share" title="Share" onClick={onShare} data-testid="share">
          <ShareIcon fontSize="small" />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="reveal hidden elements"
          title="Reveal hidden elements"
          disabled={readOnly}
          onClick={onUnhideAll}
          data-testid="reveal-hidden-elements">
          <VisibilityOffIcon fontSize="small" />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="reveal faded elements"
          title="Reveal faded elements"
          disabled={readOnly}
          onClick={onUnfadeAll}
          data-testid="reveal-faded-elements">
          <TonalityIcon fontSize="small" />
        </IconButton>

        <div className={classes.subscribers}>
          {subscribers.map((subscriber) => (
            <Tooltip title={subscriber.username} arrow key={subscriber.username}>
              <Avatar classes={{ root: classes.avatar }}>{subscriber.username.substring(0, 1).toUpperCase()}</Avatar>
            </Tooltip>
          ))}
        </div>
      </div>
      {modalElement}
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
