/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
  IconOverlay,
  Selection,
  useData,
  useSelection,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import PlayCircleOutlineIcon from '@mui/icons-material/PlayCircleOutline';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Grow from '@mui/material/Grow';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import MenuList from '@mui/material/MenuList';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import Skeleton from '@mui/material/Skeleton';
import { SxProps, Theme, useTheme } from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import {
  ComponentType,
  ForwardedRef,
  forwardRef,
  RefObject,
  useEffect,
  useImperativeHandle,
  useRef,
  useState,
} from 'react';
import { useTranslation } from 'react-i18next';
import { List as FixedSizeList, RowComponentProps } from 'react-window';
import { makeStyles } from 'tss-react/mui';
import { SynchronizationButton } from '../SynchronizationButton';
import {
  ExpressionAreaHandle,
  ExpressionAreaProps,
  ExpressionResultViewerProps,
  QueryViewConfiguration,
  QueryViewState,
  ResultAreaProps,
  ResultSplitButtonProps,
  ResultSplitButtonState,
} from './QueryView.types';
import { queryViewResultButtonExtensionPoint } from './QueryViewExtensionPoints';
import { QueryResultButtonContribution } from './QueryViewExtensionPoints.types';
import { useEvaluateExpression } from './useEvaluateExpression';
import {
  GQLBooleanExpressionResult,
  GQLIntExpressionResult,
  GQLObject,
  GQLObjectExpressionResult,
  GQLObjectsExpressionResult,
  GQLStringExpressionResult,
  GQLStringsExpressionResult,
} from './useEvaluateExpression.types';
import { useExpression } from './useExpression';
import { useQueryViewHandle } from './useQueryViewHandle';

const useQueryViewStyles = makeStyles()((theme) => ({
  view: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto 1fr',
    justifyItems: 'stretch',
    overflow: 'auto',
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    overflow: 'hidden',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    justifyContent: 'right',
    alignItems: 'center',
    borderBottomColor: theme.palette.divider,
  },
  content: {
    overflow: 'auto',
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
  },
}));

export const QueryView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  (
    { id, editingContextId, initialConfiguration, readOnly }: WorkbenchViewComponentProps,
    ref: ForwardedRef<WorkbenchViewHandle>
  ) => {
    const queryViewStyle: SxProps<Theme> = (theme) => ({
      display: 'flex',
      flexDirection: 'column',
      gap: theme.spacing(2),
      paddingX: theme.spacing(1),
      overflow: 'auto',
    });

    const [state, setState] = useState<QueryViewState>({
      objectIds: [],
      pinned: false,
    });

    const applySelection = (selection: Selection) => {
      const newObjetIds = selection.entries.map((entry) => entry.id);
      setState((prevState) => ({
        ...prevState,
        objectIds: newObjetIds,
      }));
    };

    const expressionAreaRef: RefObject<ExpressionAreaHandle | null> = useRef<ExpressionAreaHandle | null>(null);
    useQueryViewHandle(id, expressionAreaRef, applySelection, ref);

    const { selection } = useSelection();
    useEffect(() => {
      if (!state.pinned) {
        applySelection(selection);
      }
    }, [selection, state.pinned]);

    const { classes } = useQueryViewStyles();
    const { evaluateExpression, loading, result } = useEvaluateExpression(state.objectIds);
    const handleEvaluateExpression = (expression: string) => evaluateExpression(editingContextId, expression);

    const initialQueryViewConfiguration: QueryViewConfiguration =
      initialConfiguration as unknown as QueryViewConfiguration;
    const initialQueryText = initialQueryViewConfiguration?.queryText ?? null;

    const toolbar = (
      <SynchronizationButton
        pinned={state.pinned}
        onClick={() => setState((prevState) => ({ ...prevState, pinned: !prevState.pinned }))}
      />
    );

    const contents: JSX.Element = (
      <Box data-representation-kind="query" sx={queryViewStyle}>
        <ExpressionArea
          editingContextId={editingContextId}
          initialQueryText={initialQueryText}
          onEvaluateExpression={handleEvaluateExpression}
          disabled={loading || readOnly}
          ref={expressionAreaRef}
        />
        <ResultArea loading={loading} payload={result} />
      </Box>
    );
    return (
      <div className={classes.view}>
        <div className={classes.toolbar}>{toolbar}</div>
        <div className={classes.content}>{contents}</div>
      </div>
    );
  }
);

const ExpressionArea = forwardRef<ExpressionAreaHandle, ExpressionAreaProps>(
  (
    { editingContextId, initialQueryText, onEvaluateExpression, disabled }: ExpressionAreaProps,
    ref: ForwardedRef<ExpressionAreaHandle>
  ) => {
    const { expression, onExpressionChange } = useExpression(editingContextId, initialQueryText);

    useImperativeHandle(
      ref,
      () => {
        return {
          getExpression: () => expression,
        };
      },
      [expression]
    );

    const expressionAreaToolbarStyle: SxProps<Theme> = {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'space-between',
    };

    const handleKeyDown: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
      if ('Enter' === event.key && (event.ctrlKey || event.metaKey) && !disabled) {
        onEvaluateExpression(expression);
      }
    };
    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'expressionArea' });
    var isApple = /(Mac|iPhone|iPod|iPad)/i.test(navigator.platform);

    return (
      <div data-role="expression">
        <Box sx={expressionAreaToolbarStyle}>
          <Typography variant="subtitle2">{t('expression')}</Typography>

          <Tooltip title={`${t('executeExpression')} ${isApple ? '⌘' : 'Ctrl'} + Enter`}>
            <IconButton
              onClick={() => onEvaluateExpression(expression)}
              color={expression.trim().length > 0 ? 'primary' : undefined}
              disabled={disabled || expression.trim().length === 0}>
              <PlayCircleOutlineIcon />
            </IconButton>
          </Tooltip>
        </Box>
        <div>
          <TextField
            placeholder={t('texfieldPlaceholder')}
            data-testid="query-textfield"
            value={expression}
            onChange={onExpressionChange}
            onKeyDown={handleKeyDown}
            disabled={disabled}
            spellCheck={false}
            multiline
            minRows={5}
            fullWidth
            autoFocus
            sx={{
              backgroundColor: '#fff8e5',
            }}
          />
        </div>
      </div>
    );
  }
);

const ObjectExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'objectExpressionResultViewer' });
  if (result.__typename !== 'ObjectExpressionResult') {
    return null;
  }

  const { objectValue } = result as GQLObjectExpressionResult;

  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });
  const listItemIconStyle: SxProps<Theme> = () => ({
    minWidth: '0px',
  });
  return (
    <Box sx={{ overflow: 'auto' }}>
      <Typography variant="body2" gutterBottom>
        {t('oneObjectReturned')}
      </Typography>
      <Box sx={(theme) => ({ display: 'flex', flexDirection: 'column', gap: theme.spacing(1), overflow: 'auto' })}>
        <List dense>
          <ListItem sx={listItemStyle}>
            <ListItemIcon sx={listItemIconStyle}>
              <IconOverlay iconURLs={objectValue.iconURLs} alt={t('iconOverlayAlt')} />
            </ListItemIcon>
            <ListItemText primary={objectValue.label} />
          </ListItem>
        </List>
        <Box sx={{ display: 'flex', flexDirection: 'row' }}>
          <ResultSplitButton objectIds={[objectValue.id]} />
        </Box>
      </Box>
    </Box>
  );
};

const ObjectRow = ({ data, index, style }: RowComponentProps<{ data: GQLObject[] }>) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'objectRow' });
  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });
  const listItemIconStyle: SxProps<Theme> = () => ({
    minWidth: '0px',
  });

  const object = data[index];

  return (
    <ListItem key={index} style={style} sx={listItemStyle}>
      <ListItemIcon sx={listItemIconStyle}>
        <IconOverlay iconURLs={object.iconURLs} alt={t('iconOverlayAlt')} />
      </ListItemIcon>
      <ListItemText primary={object.label} />
    </ListItem>
  );
};

const ObjectsExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'ObjectsExpressionResult') {
    return null;
  }

  const { objectsValue } = result as GQLObjectsExpressionResult;
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'objectsExpressionResultViewer' });

  const listStyle: SxProps<Theme> = (theme) => ({
    border: `1px solid ${theme.palette.divider}`,
    marginBottom: '1px',
    overflow: 'auto',
  });

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', overflow: 'auto' }}>
      <Typography variant="body2" gutterBottom>
        {t('objectsReturned', { count: objectsValue.length })}
      </Typography>
      <Box
        sx={(theme) => ({
          display: 'flex',
          flexDirection: 'column',
          gap: theme.spacing(1),
          paddingBottom: theme.spacing(1),
          overflow: 'auto',
        })}>
        <List dense disablePadding sx={listStyle}>
          <FixedSizeList
            rowComponent={ObjectRow}
            rowProps={{ data: objectsValue }}
            rowCount={objectsValue.length}
            rowHeight={34}
          />
        </List>
        <Box sx={{ display: 'flex', flexDirection: 'row' }}>
          <ResultSplitButton objectIds={objectsValue.map((objectValue) => objectValue.id)} />
        </Box>
      </Box>
    </Box>
  );
};

const BooleanExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'booleanExpressionResultViewer' });
  if (result.__typename !== 'BooleanExpressionResult') {
    return null;
  }

  const { booleanValue } = result as GQLBooleanExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        {t('oneBooleanReturned')}
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={booleanValue.toString()} />
        </ListItem>
      </List>
    </Box>
  );
};

const StringRow = ({ data, index, style }: RowComponentProps<{ data: string[] }>) => {
  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });

  const value = data[index];

  return (
    <ListItem key={index} style={style} sx={listItemStyle}>
      <ListItemText primary={value} />
    </ListItem>
  );
};

const StringsExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'stringsExpressionResultViewer' });
  if (result.__typename !== 'StringsExpressionResult') {
    return null;
  }

  const { stringsValue } = result as GQLStringsExpressionResult;

  const listStyle: SxProps<Theme> = (theme) => ({
    border: `1px solid ${theme.palette.divider}`,
  });

  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        {t('stringsReturned', { count: stringsValue.length })}
      </Typography>
      <List dense disablePadding sx={listStyle}>
        <FixedSizeList
          rowComponent={StringRow}
          rowProps={{ data: stringsValue }}
          rowCount={stringsValue.length}
          rowHeight={34}
        />
      </List>
    </Box>
  );
};

const StringExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'stringExpressionResultViewer' });
  if (result.__typename !== 'StringExpressionResult') {
    return null;
  }

  const { stringValue } = result as GQLStringExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        {t('stringReturned')}
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={stringValue} />
        </ListItem>
      </List>
    </Box>
  );
};

const IntExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'intExpressionResultViewer' });
  if (result.__typename !== 'IntExpressionResult') {
    return null;
  }

  const { intValue } = result as GQLIntExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        {t('intReturned')}
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={intValue} />
        </ListItem>
      </List>
    </Box>
  );
};

const VoidExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'voidExpressionResultViewer' });
  if (result.__typename !== 'VoidExpressionResult') {
    return null;
  }

  return <Typography variant="body2">{t('voidReturned')}</Typography>;
};

const resultType2viewer: Record<string, ComponentType<ExpressionResultViewerProps>> = {
  ObjectExpressionResult: ObjectExpressionResultViewer,
  ObjectsExpressionResult: ObjectsExpressionResultViewer,
  BooleanExpressionResult: BooleanExpressionResultViewer,
  StringsExpressionResult: StringsExpressionResultViewer,
  StringExpressionResult: StringExpressionResultViewer,
  IntExpressionResult: IntExpressionResultViewer,
  VoidExpressionResult: VoidExpressionResultViewer,
};

const LoadingViewer = () => {
  const theme = useTheme();

  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });
  const skeletonTextStyle: SxProps<Theme> = (theme) => ({
    fontSize: theme.typography.body1.fontSize,
    width: '60%',
  });
  return (
    <Box>
      <Skeleton variant="text" sx={{ fontSize: theme.typography.body2.fontSize }} />
      <List dense>
        <ListItem sx={listItemStyle}>
          <Skeleton variant="circular" width={16} height={16} />
          <Skeleton variant="text" sx={skeletonTextStyle} />
        </ListItem>
        <ListItem sx={listItemStyle}>
          <Skeleton variant="circular" width={16} height={16} />
          <Skeleton variant="text" sx={skeletonTextStyle} />
        </ListItem>
        <ListItem sx={listItemStyle}>
          <Skeleton variant="circular" width={16} height={16} />
          <Skeleton variant="text" sx={skeletonTextStyle} />
        </ListItem>
      </List>
    </Box>
  );
};

const ResultArea = ({ loading, payload }: ResultAreaProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'resultArea' });
  const resultAreaToolbarStyle: SxProps<Theme> = {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  };

  const titleAreaStyle: SxProps<Theme> = {
    display: 'flex',
    flexDirection: 'column',
  };

  let content: JSX.Element | null = null;
  if (loading) {
    content = <LoadingViewer />;
  } else if (payload) {
    const Viewer = resultType2viewer[payload.result.__typename];
    if (Viewer) {
      content = <Viewer result={payload.result} />;
    } else {
      content = <Typography variant="body2">{t('unsupportedResult')}</Typography>;
    }
  }

  return (
    <div data-role="result" style={{ overflow: 'auto', display: 'flex', flexDirection: 'column' }}>
      <Box sx={resultAreaToolbarStyle}>
        <Box sx={titleAreaStyle}>
          <Typography variant="subtitle2">{t('evaluationResult')}</Typography>
        </Box>
      </Box>

      {content}
    </div>
  );
};

const ResultSplitButton = ({ objectIds }: ResultSplitButtonProps) => {
  const { data: queryResultButtonContributions } = useData<QueryResultButtonContribution[]>(
    queryViewResultButtonExtensionPoint
  );

  const [state, setState] = useState<ResultSplitButtonState>({
    selected: false,
    open: false,
    selectedIndex: 0,
    message: '',
  });

  const buttonGroupRef = useRef<HTMLDivElement>(null);
  const widgetRef = useRef<HTMLDivElement>(null);

  const handleMenuItemClick = (_event, index) => {
    setState((prevState) => ({ ...prevState, open: false, selectedIndex: index }));
  };

  const handleToggle = () => {
    setState((prevState) => ({ ...prevState, open: !prevState.open }));
  };

  const handleClose = (event) => {
    event.preventDefault();
    setState((prevState) => ({ ...prevState, open: false }));
  };

  if (queryResultButtonContributions.length === 0) {
    return null;
  }

  const QueryResultButton = queryResultButtonContributions.at(state.selectedIndex).component;

  return (
    <div ref={widgetRef}>
      <ButtonGroup
        variant="contained"
        color="primary"
        ref={buttonGroupRef}
        aria-label="split button"
        onFocus={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: true,
            };
          })
        }
        onBlur={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: false,
            };
          })
        }>
        <QueryResultButton objectIds={objectIds} />
        <Button
          color="primary"
          size="small"
          aria-controls={state.open ? 'split-button-menu' : undefined}
          aria-expanded={state.open ? 'true' : undefined}
          aria-label="select button action"
          aria-haspopup="menu"
          role={'show-actions'}
          onClick={handleToggle}>
          <ArrowDropDownIcon />
        </Button>
      </ButtonGroup>
      <Popper
        open={state.open}
        anchorEl={buttonGroupRef.current}
        transition
        placement="bottom"
        style={{ zIndex: 1400 }}>
        {({ TransitionProps, placement }) => (
          <Grow
            {...TransitionProps}
            style={{
              transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom',
            }}>
            <Paper>
              <ClickAwayListener onClickAway={handleClose}>
                <MenuList id="split-button-menu">
                  {queryResultButtonContributions.map((action, index) => (
                    <MenuItem
                      key={index}
                      selected={index === state.selectedIndex}
                      onClick={(event) => handleMenuItemClick(event, index)}>
                      {action.label}
                    </MenuItem>
                  ))}
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
    </div>
  );
};
